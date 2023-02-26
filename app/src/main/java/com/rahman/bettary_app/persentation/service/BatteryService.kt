package com.rahman.bettary_app.persentation.service

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.rahman.bettary_app.R
import com.rahman.bettary_app.db.entity.BatteryED
import com.rahman.bettary_app.persentation.AddressActivity
import com.rahman.bettary_app.persentation.util.TimeUtility
import com.rahman.educationinfo.repository.BatteryRepositoryImp
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

    private lateinit var notification: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManager
    private lateinit var batteryManager: BatteryManager

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager;
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
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
        return super.onStartCommand(intent, flags, startId)
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
            .setSubText("hello")
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
//
//            notificationBuilder.setCustomBigContentView(customView)
//            notificationBuilder.setCustomHeadsUpContentView(customView)
//
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(getString(R.string.channel_id))
        }

        // check have active notification
        notificationManager.notify(123,notificationBuilder.build())


    }

    private fun updateNotification() {

        val currentNow = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW)
        val level = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
//        val en = batteryManager.getIntProperty(BatteryManager.Batter)


        val voltage = batteryManager.getIntProperty(BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE)

        serviceScope.launch {
            batteryRepositoryImp.insertOne(
                BatteryED(
                    uid = System.currentTimeMillis().toInt(),
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