package com.rahman.bettary_app.persentation.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.rahman.bettary_app.persentation.AddressActivity

class HeadsUpNotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

//        performClickAction();

    }

    private fun performClickAction(context: Context, action: String, data: Bundle?) {

            var openIntent: Intent? = null
            try {
                openIntent = Intent(
                    context,
                    AddressActivity::class.java,
                )
                context.startActivity(openIntent)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }

    }

}