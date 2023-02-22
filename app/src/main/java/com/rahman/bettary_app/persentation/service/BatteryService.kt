package com.rahman.bettary_app.persentation.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.BatteryState
import android.os.*
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.rahman.bettary_app.R
import com.rahman.bettary_app.persentation.MainActivity
import com.rahman.bettary_app.persentation.contentProvider.RxBattery
import com.rahman.bettary_app.persentation.contentProvider.RxBatteryManager
import com.rahman.bettary_app.persentation.viewModel.BatteryViewModel
import com.rahman.educationinfo.repository.BatteryRepositoryImp
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.*
import javax.inject.Inject

class BatteryService : Service() {

    @Inject lateinit var batteryRepositoryImp: BatteryRepositoryImp



    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        showMainNotification()
        showChargingOptionNotification()
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("CheckResult", "RemoteViewLayout")
    private fun showChargingOptionNotification() {



//                if(it.statusCode == BatteryState.STATUS_CHARGING){
//                    val intent = Intent(this, MainActivity::class.java).apply {
//                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    }
//                    val pendingIntent: PendingIntent =
//                        PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
//
//
//                    val notificationLayout = RemoteViews(this.packageName, R.layout.view_notification_collapsed)
//
//                    val builder = NotificationCompat.Builder(this, getString(R.string.channel_id))
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                        .setAutoCancel(true)
//                        .setShowWhen(true)
//
//                        .setPriority(NotificationCompat.PRIORITY_MAX)
//                        .setCategory(NotificationCompat.CATEGORY_SOCIAL)
//                        .setCustomContentView(notificationLayout)
//                        //.setCustomBigContentView(notificationLayout)
//                        .setContentIntent(pendingIntent)
//
//                    var res = builder.build();
//                    res.flags = Notification.FLAG_NO_CLEAR or Notification.PRIORITY_HIGH
//                    startForeground(123, res);
//                }



    }

    @SuppressLint("CheckResult")
    private fun showMainNotification() {


        Log.i("BatteryService", "showNotification: background service is running");

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)



        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val builder = NotificationCompat.Builder(this, getString(R.string.channel_id))
            .setSmallIcon(coil.compose.singleton.R.drawable.notification_icon_background)
            .setContentTitle("Battery Service")
            .setContentText("Battery Service is running")
            .setAutoCancel(false)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setFullScreenIntent(pendingIntent,true)


            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_CALL)
            .setWhen(System.currentTimeMillis())






        var res = builder.build();
        res.flags = Notification.FLAG_NO_CLEAR or Notification.PRIORITY_HIGH

        RxBattery.observe(this)
            .subscribeOn(Schedulers.io())
            .subscribe({

            }, {
                Log.i("BatteryService", "showChargingOptionNotification: ${it.message}")
            })
        RxBatteryManager.observe(this).subscribeOn(Schedulers.io()).
            subscribe {

            }

        startForeground(1, res);

    }

    // I take advantage of (count on) R.java having resources alphabetical and incrementing by one.
    private fun iconFor(percent: Int): Int {

        return R.drawable.charging000 + percent
    }
}