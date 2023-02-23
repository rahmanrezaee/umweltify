//package com.rahman.bettary_app.persentation.service
//
//import android.annotation.SuppressLint
//import android.app.Notification
//import android.app.NotificationManager
//import android.app.PendingIntent
//import android.app.Service
//import android.appwidget.AppWidgetManager
//import android.appwidget.AppWidgetProvider
//import android.content.ComponentName
//import android.content.Context
//import android.content.Intent
//import android.os.*
//import android.util.Log
//import android.widget.RemoteViews
//import androidx.core.app.NotificationCompat
//import com.rahman.bettary_app.R
//import com.rahman.bettary_app.db.entity.BatteryED
//import com.rahman.bettary_app.persentation.MainActivity
//import com.rahman.bettary_app.persentation.contentProvider.BatteryManagerState
//import com.rahman.bettary_app.persentation.contentProvider.BatteryStateBroadcast
//import com.rahman.bettary_app.persentation.contentProvider.RxBattery
//import com.rahman.bettary_app.persentation.contentProvider.RxBatteryManager
//import com.rahman.educationinfo.repository.BatteryRepositoryImp
//import dagger.hilt.android.AndroidEntryPoint
//import io.reactivex.rxjava3.core.Flowable
//import io.reactivex.rxjava3.schedulers.Schedulers
//import kotlinx.coroutines.*
//import java.io.*
//import javax.inject.Inject
//
//
//@AndroidEntryPoint
//class BatteryService : Service() {
//
//    @Inject
//    lateinit var batteryRepositoryImp: BatteryRepositoryImp
//    private lateinit var batteryStatus: BatteryStateBroadcast
//    private lateinit var rxBattery : Flowable<BatteryStateBroadcast>
//    private lateinit var rxBatteryManager : Flowable<BatteryManagerState>
//    private val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//
//
//    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
//
//
//    override fun onCreate() {
//        super.onCreate()
//        rxBattery =  RxBattery.observe(this)
//            .subscribeOn(Schedulers.io())
//
//
//
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        when(intent?.action) {
//            ACTION_START -> start()
//            ACTION_STOP -> stop()
//        }
//        return super.onStartCommand(intent, flags, startId)
//    }
//
//    private fun start() {
//        val notification = NotificationCompat.Builder(this, "location")
//            .setContentTitle("Tracking location...")
//            .setContentText("Location: null")
//            .setSmallIcon(R.drawable.ic_launcher_background)
//            .setOngoing(true)
//
//
//
//
//
//        showMainNotification()
////        showChargingOptionNotification()
//
//
//
//        startForeground(1, notification.build())
//    }
//
//    private fun stop() {
//        stopForeground(true)
//        stopSelf()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        serviceScope.cancel()
//    }
//
//
//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }
//
//
//    @SuppressLint("CheckResult", "RemoteViewLayout")
//    private fun showChargingOptionNotification() {
//
//
////                if(it.statusCode == BatteryState.STATUS_CHARGING){
////                    val intent = Intent(this, MainActivity::class.java).apply {
////                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
////                    }
////                    val pendingIntent: PendingIntent =
////                        PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
////
////
////                    val notificationLayout = RemoteViews(this.packageName, R.layout.view_notification_collapsed)
////
////                    val builder = NotificationCompat.Builder(this, getString(R.string.channel_id))
////                        .setSmallIcon(R.mipmap.ic_launcher)
////                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
////                        .setAutoCancel(true)
////                        .setShowWhen(true)
////
////                        .setPriority(NotificationCompat.PRIORITY_MAX)
////                        .setCategory(NotificationCompat.CATEGORY_SOCIAL)
////                        .setCustomContentView(notificationLayout)
////                        //.setCustomBigContentView(notificationLayout)
////                        .setContentIntent(pendingIntent)
////
////                    var res = builder.build();
////                    res.flags = Notification.FLAG_NO_CLEAR or Notification.PRIORITY_HIGH
////                    startForeground(123, res);
////                }
//
//
//    }
//
//    @SuppressLint("CheckResult", "RemoteViewLayout")
//    private fun showMainNotification() {
//
//        rxBattery
//            .subscribe({
//                batteryStatus = it;
//
//            }, {
//
//            })
//        rxBatteryManager.subscribe { battery ->
//            GlobalScope.launch {
//                batteryRepositoryImp.insertOne(
//                    BatteryED(
//                        uid =  System.currentTimeMillis().toInt(),
//                        voltage = battery.currentNow,
//                        ampere = battery.currentNow,
//                        watt = battery.currentNow,
//                        startTime = System.currentTimeMillis(),
//                        endTime = System.currentTimeMillis() + 20000,
//                    )
//                )
//            }
//        }
//    }
//
//    // I take advantage of (count on) R.java having resources alphabetical and incrementing by one.
//    private fun iconFor(percent: Int): Int {
//
//        return R.drawable.charging000 + percent
//    }
//
//    companion object {
//        const val ACTION_START = "ACTION_START"
//        const val ACTION_STOP = "ACTION_STOP"
//    }
//}

package com.rahman.bettary_app.persentation.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.rahman.bettary_app.R
import com.rahman.bettary_app.db.entity.BatteryED
import com.rahman.bettary_app.persentation.broadcast_receiver.BatteryStateBroadcast
import com.rahman.bettary_app.persentation.broadcast_receiver.RxBattery
import com.rahman.educationinfo.repository.BatteryRepositoryImp
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class BatteryService : Service() {

    @Inject
    lateinit var batteryRepositoryImp: BatteryRepositoryImp

    private lateinit var notificationManager: NotificationManager
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val builder = NotificationCompat.Builder(this, getString(R.string.channel_id))
            .setSmallIcon(coil.compose.singleton.R.drawable.notification_icon_background)
            .setContentTitle("Battery Service")
            .setContentText("Battery Service is running")
            .setAutoCancel(false)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setWhen(System.currentTimeMillis())
        startForeground(1, builder.build());
        showChargingOptionNotification()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    }

    @SuppressLint("CheckResult", "RemoteViewLayout", "SimpleDateFormat")
    private fun showChargingOptionNotification() {

        val batteryManager: BatteryManager =
            this.getSystemService(Context.BATTERY_SERVICE) as BatteryManager;

        Observable
            .interval(10, TimeUnit.SECONDS)
            .subscribe{

                val currentNow =
                    batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW)
                val level  = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
                serviceScope.launch {
                    batteryRepositoryImp.insertOne(
                        BatteryED(
                            uid = System.currentTimeMillis().toInt(),
                            voltage = currentNow,
                            level = level,
                            isCharging = currentNow > 0,
                            ampere = currentNow,
                            watt = currentNow,
                            startTime =  System.currentTimeMillis(),
                            endTime = System.currentTimeMillis() + 10000,
                        )
                    )
                    Log.i("DatabaseLog","add New $currentNow $level - ${convertLongToTime(System.currentTimeMillis())}")
                }
            }
    }


    // I take advantage of (count on) R.java having resources alphabetical and incrementing by one.
    private fun iconFor(percent: Int): Int {

        return R.drawable.charging000 + percent
    }

    @SuppressLint("SimpleDateFormat")
    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
        return format.format(date)
    }

}