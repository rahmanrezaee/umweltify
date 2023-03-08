package com.rahman.bettary_app.persentation.service

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.BatteryManager
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.rahman.bettary_app.R
import com.rahman.bettary_app.db.entity.BatteryED
import com.rahman.bettary_app.domain.model.BatteryModel
import com.rahman.bettary_app.persentation.AddressActivity
import com.rahman.bettary_app.persentation.BaseApplication
import com.rahman.bettary_app.persentation.constants.SharedConstant
import com.rahman.bettary_app.persentation.util.TimeUtility
import com.rahman.bettary_app.repository.AddressRepository
import com.rahman.bettary_app.repository.BatteryRepositoryImp
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class BatteryService : Service() {

    @Inject
    lateinit var batteryRepositoryImp: BatteryRepositoryImp

    @Inject
    lateinit var addressRepository: AddressRepository

    @Inject
    lateinit var baseContext: BaseApplication

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var deviceId: String
    private lateinit var notification: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManager
    private lateinit var batteryManager: BatteryManager
    private lateinit var context: Context;
    private var addressId: Int = -1;
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @SuppressLint("HardwareIds")
    override fun onCreate() {
        super.onCreate()
        context = this;
        batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager;
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        notification = NotificationCompat.Builder(this, getString(R.string.channel_id))
            .setSmallIcon(R.drawable.loading)
            .setContentTitle("Battery Status").setContentText("Waiting To Setup")
            .setAutoCancel(false).setOngoing(true).setOnlyAlertOnce(true)
            .setDefaults(Notification.DEFAULT_ALL).setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM).setWhen(System.currentTimeMillis())
        startForeground(1, notification.build());
        showChargingOptionNotification()
        listenToAddressBroadCast()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun listenToAddressBroadCast() {

        var connectionBroadcastReceiver = object : AddressActionReceiver() {
            override fun broadcastResult(address: Int) {
                with(sharedPreferences.edit()) {
                    putInt(getString(R.string.address_key), address)
                    apply()
                }
                Toast.makeText(context, "Custom address $address", Toast.LENGTH_SHORT).show()
            }
        }
        val intentFilter = IntentFilter()
        intentFilter.addAction(BroadCastConst.addressIntent)
        this.registerReceiver(connectionBroadcastReceiver, intentFilter)

    }

    private var chargeState: BatteryStateBroadCast? = null
    private var groupId: String? = null

    @SuppressLint("CheckResult")
    private fun showChargingOptionNotification() {

        BatteryReceiver.observe(this).subscribeOn(Schedulers.io()).subscribe { batteryState ->

            Log.i(
                "BatteryService",
                "ChargingState ${chargeState?.isCharging} ${batteryState.isCharging}"
            )
            if (chargeState?.isCharging == null || chargeState?.isCharging != batteryState.isCharging) {
                Log.i("BatteryService", "ShowNotification")

                if (chargeState?.isCharging != null && batteryState.isCharging) {
                    try {
                        serviceScope.launch {
                            addressRepository.getAll().take(1).collect { items ->
                                if (items.isNotEmpty()) {
                                    val selectedAddress =
                                        sharedPreferences.getString(SharedConstant.addressName, "")
                                    if (selectedAddress != null && selectedAddress.isNotBlank()) {
                                        if (!(items.size == 1 && items.first().placeName == selectedAddress)) {
                                            showSelectLocationNotification()
                                        }
                                    } else {
                                        showSelectLocationNotification()
                                    }
                                }
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
                if (chargeState?.isCharging != null) {
                    sendBackgroundData()
                }
                chargeState = batteryState;
                groupId = UUID.randomUUID().toString();
                updateNotification()

            }
        }
        Observable.interval(20, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).subscribe {
            if (chargeState?.isCharging != null)
                updateNotification()
        }

    }

    private fun sendBackgroundData() {


        var lat = sharedPreferences.getFloat(SharedConstant.addressLat, 0f);
        var long = sharedPreferences.getFloat(SharedConstant.addressLon, 0f);
        Log.i("serviceConsole", "lat $lat, long $long")


        serviceScope.launch {


            var lastRow = batteryRepositoryImp.getLastItem();
            lastRow.group?.let {
                var result = batteryRepositoryImp.getGroupForService(it);

                if (result.size > 3) {

                    val startTime =
                        TimeUtility.convertUTC(result.first().startTime);

                    val endTime =
                        TimeUtility.convertUTC(result.last().endTime!!);

                    val batteryBody = BatteryModel(
                        userId = "",
                        averageVoltage = getAverage(
                            result.map { item ->
                                item.voltage!!
                            }.toList()
                        ),
                        averageAmpere = getAverage(
                            result.map { item ->
                                item.ampere!!
                            }.toList()
                        ),
                        totalWatts = result.map { item ->
                            item.ampere!!
                        }.toList().sum().toDouble(),
//            latitude = 65.00000,
//            longitude = 54.3424233,
                        deviceId = deviceId,
                        to = startTime,
                        from = endTime,
                    );


                    batteryRepositoryImp.insertToServer(batteryBody).onSuccess { data ->
                        Log.i("ResultBattery", "Result ${data.toString()}")

                    }.onFailure {
                        Log.i("ResultBattery", "Error ${it.localizedMessage!!}")
                    }

                }

            }


        }

    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun showSelectLocationNotification() {


        val fullScreenIntent = Intent(this, AddressActivity::class.java)

        fullScreenIntent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK
        fullScreenIntent.action = Intent.ACTION_MAIN;
        fullScreenIntent.addCategory(Intent.CATEGORY_LAUNCHER)

        val fullScreenPendingIntent =
            PendingIntent.getActivity(
                this, 0, fullScreenIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        val mPowerManager = this.getSystemService(Context.POWER_SERVICE) as PowerManager
        val mWakeLock: PowerManager.WakeLock = mPowerManager.newWakeLock(
            PowerManager.SCREEN_DIM_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
            "YourApp:Whatever"
        )
        mWakeLock.acquire(60000L /*10 minutes*/)

        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this)
            .setSmallIcon(android.R.drawable.ic_delete)
            .setContentTitle("Select Address")
            .setContentText("Please Select a Address")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setCategory(NotificationCompat.CATEGORY_CALL) // Use a full-screen intent only for the highest-priority alerts where you
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .setColorized(true)
            .setSilent(true)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .setAutoCancel(true)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val customView = RemoteViews(
//                packageName,
//                R.layout.view_notification_collapsed
//            )
//            customView.setTextViewText(R.id.name, "New Address")
//            notificationBuilder.setCustomBigContentView(customView)
//            notificationBuilder.setCustomHeadsUpContentView(customView)
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(getString(R.string.channel_id))
        }

        // check have active notification
        notificationManager.notify(123, notificationBuilder.build())


    }

    override fun onDestroy() {
        super.onDestroy()


    }

    private fun updateNotification() {

        val currentNow = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW)
        val level = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
//        val en = batteryManager.getIntProperty(BatteryManager.Batter)

        val voltage = batteryManager.getIntProperty(BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE)

        serviceScope.launch {
            batteryRepositoryImp.insertOne(
                BatteryED(
                    voltage = chargeState?.voltage,
                    level = level,
                    isCharging = chargeState?.isCharging,
                    group = groupId,
                    ampere = currentNow,
                    watt = currentNow,
                    startTime = System.currentTimeMillis(),
                    endTime = System.currentTimeMillis() + 10000,

                    )
            )
            val updatedNotification = notification
                .setContentTitle("volt: $currentNow")
                .setSmallIcon(iconFor(level))
                .setContentText("Charging")

            notificationManager.notify(1, updatedNotification.build())
            Log.i(
                "DatabaseLog",
                "add New $currentNow ${chargeState?.voltage} $groupId - ${
                    TimeUtility.convertLongToTime(
                        System.currentTimeMillis()
                    )
                }"
            )
        }
    }

    private fun iconFor(percent: Int): Int {
        return R.drawable.charging000 + percent
    }

}

fun getAverage(list: List<Int>): Double {
    var sum: Long = 0
    for (i in list) {
        sum += i.toLong()
    }
    return if (list.isNotEmpty()) sum.toDouble() / list.size else 0.0
}

fun getTota(list: List<Int>): Double {
    var sum: Long = 0
    for (i in list) {
        sum += i.toLong()
    }
    return if (list.isNotEmpty()) sum.toDouble() / list.size else 0.0
}