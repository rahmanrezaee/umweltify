package com.rahman.bettary_app.persentation.service

import android.R
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.rahman.bettary_app.persentation.AddressActivity
import kotlinx.coroutines.*
import okhttp3.internal.notify


class HeadsUpNotificationService : Service() {


    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private lateinit var context: Context

    override fun onCreate() {
        super.onCreate()
        context = this
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val notification = buildNotification("Background Call")
        startForeground(11, notification)

        serviceScope.launch {
            delay(5000L)
            val notification = buildNotification("Lock Screen Call",true)

            val mNotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            mNotificationManager.notify(1,notification)
            sendBroadcast(Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS))
        }

        return START_NOT_STICKY
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun buildNotification(title:String,showFull:Boolean = false): Notification? {

        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_delete)
            .setContentTitle("$title")
            .setContentText("(919) 555-1234")
//            .setAutoCancel(true)tru


            // Use a full-screen intent only for the highest-priority alerts where you
            if(showFull){
                val fullScreenIntent = Intent(this, AddressActivity::class.java)
                val fullScreenPendingIntent =
                    PendingIntent.getActivity(this, 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT)


                notificationBuilder.setFullScreenIntent(fullScreenPendingIntent, true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_CALL);

            }else{
                notificationBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setCategory(NotificationCompat.CATEGORY_CALL);
            }

        notificationBuilder.setAutoCancel(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    "123",
                    "123",
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
            notificationBuilder.setChannelId("123")
        }
        return notificationBuilder.build()
    }
}