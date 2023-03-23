package com.rahman.umweltify.presentation.service

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
import com.rahman.umweltify.R
import com.rahman.umweltify.db.entity.BatteryED
import com.rahman.umweltify.domain.model.BatteryModel
import com.rahman.umweltify.presentation.AddressActivity
import com.rahman.umweltify.presentation.BaseApplication
import com.rahman.umweltify.presentation.MainActivity
import com.rahman.umweltify.presentation.constants.SharedConstant
import com.rahman.umweltify.presentation.util.BatteryUtil
import com.rahman.umweltify.presentation.util.TimeUtility
import com.rahman.umweltify.repository.AddressRepository
import com.rahman.umweltify.repository.BatteryRepositoryImp
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
import kotlin.math.roundToInt


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
    private var intervalRate: Int = 5;
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

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val intentMain = Intent(this, MainActivity::class.java)
        intentMain.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP or
                    Intent.FLAG_ACTIVITY_NEW_TASK
        )

        val contentIntent = PendingIntent.getActivity(
            this,
            1, intentMain,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        notification = NotificationCompat.Builder(this, getString(R.string.channel_id))
            .setSmallIcon(R.drawable.loading)
            .setContentTitle("Umweltify is running...")
            .setAutoCancel(false).setOngoing(true).setOnlyAlertOnce(true)
            .setContentIntent(contentIntent)
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
                Log.i("BatteryService", "ShowNotification ${chargeState?.isCharging}")

                if (chargeState?.isCharging != null && !batteryState.isCharging) {
                    sendBackgroundData()
                }

                if (chargeState?.isCharging != null && batteryState.isCharging) {
                    try {
                        serviceScope.launch {
                            addressRepository.getAll().take(1).collect { items ->
                                if (items.isNotEmpty()) {
                                    val selectedAddress =
                                        sharedPreferences.getString(
                                            SharedConstant.addressName,
                                            ""
                                        )
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

                chargeState = batteryState;
                groupId = UUID.randomUUID().toString();
                updateNotification()

            }
        }

        Observable.interval(intervalRate.toLong(), TimeUnit.SECONDS).subscribeOn(Schedulers.io())
            .subscribe {
                if (chargeState?.isCharging != null)
                    updateNotification()
            }

    }

    private fun sendBackgroundData() {

        var lat = sharedPreferences.getFloat(SharedConstant.addressLat, 0f);
        var long = sharedPreferences.getFloat(SharedConstant.addressLon, 0f);
        var alt = sharedPreferences.getFloat(SharedConstant.addressAlt, 0f);
        var userId = sharedPreferences.getString(SharedConstant.token, "");

        serviceScope.launch {

            var lastRow = batteryRepositoryImp.getLastItem();
            lastRow.group?.let {
                var result = batteryRepositoryImp.getGroupForService(it);

                if (result.size > 3) {
                    val startTime =
                        TimeUtility.convertUTC(result.first().startTime);

                    val startLevel = result.first().level
                    val endLevel = result.last().level


                    val endTime =
                        TimeUtility.convertUTC(result.last().endTime!!);

                    var cap = Math.round(
                        (getBatteryCapacity().div(100000).roundToInt().toDouble().times(100))
                    )

                    val averageV: Double = getAverage(
                        result.map { item ->
                            item.voltage!!.div(1000.0)
                        }.toList()
                    );

                    val averageA: Double = getAverage(
                        result.map { item ->
                            item.ampere!!.div(1000.0)
                        }.toList()
                    );

                    val totalW: Double = result.map { item -> item.watt!! }.toList().sum();

                    if (averageV > 0 && averageA > 0) {
                        val batteryBody = BatteryModel(
                            userId = userId ?: "",
                            averageVoltage = averageV,
                            averageAmpere = averageA,
                            totalWatts = totalW,
                            latitude = lat.toDouble(),
                            longitude = long.toDouble(),
                            altitude = alt.toDouble(),
                            deviceId = deviceId,
                            to = endTime,
                            from = startTime,
                            batteryCapacity = cap.toInt(),
                            batteryLevelFrom = startLevel ?: 0,
                            batteryLevelTo = endLevel ?: 0,
                            sourceType = result.last().source.toString(),
                            Interval = intervalRate,
                            TotalSamples = result.size
                        );
                        batteryRepositoryImp.insertToServer(batteryBody).onSuccess { data ->
                        }.onFailure {
                            Log.i("ResultBattery", "Error ${it.localizedMessage!!}")
                        }
                    }


                }
            }
        }

    }

    private fun getBatteryCapacity(): Double {

        val chargeCounter =
            batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER)
        val capacity = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

        return if (chargeCounter == Int.MIN_VALUE || capacity == Int.MIN_VALUE) 0.0 else (chargeCounter / capacity * 100).toDouble()

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
            .setSmallIcon(R.drawable.round_my_location)
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


        serviceScope.launch {
            batteryRepositoryImp.insertOne(
                BatteryED(
                    voltage = chargeState?.voltage,
                    level = level,
                    isCharging = chargeState?.isCharging,
                    group = groupId,
                    source = chargeState?.plugged().toString(),
                    ampere = BatteryUtil.getBatteryCurrentNowInAmperes(currentNow).toInt(),
                    watt = BatteryUtil.getBatteryCurrentNowInWatt(
                        currentNow,
                        chargeState?.voltage ?: 0
                    ),
                    startTime = System.currentTimeMillis(),
                    endTime = System.currentTimeMillis() + 5000,
                )
            )
            val updatedNotification = notification
                .setSmallIcon(iconFor(level))

            notificationManager.notify(1, updatedNotification.build())

            Log.i(
                "DatabaseLog",
                "Lat ${TimeUtility.convertUTC(System.currentTimeMillis())} ampere: ${
                    BatteryUtil.getBatteryCurrentNowInAmperes(
                        currentNow
                    )
                } voltage :${chargeState?.voltage} Watt: ${
                    BatteryUtil.getBatteryCurrentNowInWatt(
                        currentNow,
                        chargeState?.voltage ?: 0
                    )
                }"
            )

        }
    }

    private fun iconFor(percent: Int): Int {
        return R.drawable.charging000 + percent
    }

}

fun getAverage(list: List<Double>): Double {
    var sum = 0.0
    for (i in list) {
        sum += i
    }
    return if (list.isNotEmpty()) sum.div(list.size) else 0.0
}
