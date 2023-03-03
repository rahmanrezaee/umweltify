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
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.rahman.bettary_app.R
import com.rahman.bettary_app.db.entity.AddressED
import com.rahman.bettary_app.db.entity.BatteryED
import com.rahman.bettary_app.domain.model.BatteryModel
import com.rahman.bettary_app.persentation.AddressActivity
import com.rahman.bettary_app.persentation.BaseApplication
import com.rahman.bettary_app.persentation.util.TimeUtility
import com.rahman.bettary_app.repository.BatteryRepositoryImp
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class BatteryService : Service() {

    @Inject
    lateinit var batteryRepositoryImp: BatteryRepositoryImp

    @Inject
    lateinit var baseContext: BaseApplication
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var deviceId: String

    private lateinit var notification: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManager
    private lateinit var batteryManager: BatteryManager
    private lateinit var context: Context;
    private  var addressId:Int = -1;


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

                with (sharedPreferences.edit()) {
                    putInt(getString(R.string.address_key), address)
                    apply()
                }

                Toast.makeText(context,"Custom address $address", Toast.LENGTH_SHORT).show()
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

        BatteryReceiver.observe(this).subscribeOn(Schedulers.io()).subscribe {

            Log.i("BatteryService","ChargingState ${chargeState?.isCharging} ${it.isCharging}")
            if (chargeState?.isCharging == null || chargeState?.isCharging != it.isCharging) {
            Log.i("BatteryService","ShowNotification")


                if(chargeState?.isCharging != null && it.isCharging){
                    showSelectLocationNotification()
                }
                if(chargeState?.isCharging != null){
                    sendBackgroundData()
                }
                chargeState = it;
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

        val batteryBody = BatteryModel(
            userId = "",
            averageVoltage = 4500.0,
            latitude = 65.00000,
            longitude = 54.3424233,
            deviceId = deviceId,
            deviceManufacture = Build.MANUFACTURER,
            usageTo = "2022-12-05 00:20:00",
            usageFrom = "2022-12-05 00:00:00",
            averageMA = 2.34,
            totalWh = 1.400
        );
        serviceScope.launch {
            batteryRepositoryImp.insertToServer(batteryBody);
        }

    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun showSelectLocationNotification() {

        val fullScreenIntent = Intent(this, AddressActivity::class.java)

        fullScreenIntent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_NEW_TASK

        val fullScreenPendingIntent =
            PendingIntent.getActivity(this, 0, fullScreenIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val mPowerManager = this.getSystemService(Context.POWER_SERVICE) as PowerManager
        val mWakeLock: PowerManager.WakeLock = mPowerManager.newWakeLock(
            PowerManager.SCREEN_DIM_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
            "YourApp:Whatever"
        )
        mWakeLock.acquire(60000L /*10 minutes*/)

        val notificationBuilder : NotificationCompat.Builder = NotificationCompat.Builder(this)
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
        notificationManager.notify(123,notificationBuilder.build())


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
                "add New $currentNow ${chargeState?.voltage} $groupId - ${TimeUtility.convertLongToTime(System.currentTimeMillis())}"
            )
        }
    }

    private fun iconFor(percent: Int): Int {

        return R.drawable.charging000 + percent
    }






}