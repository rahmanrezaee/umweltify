package com.rahman.umweltify.presentation.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import androidx.core.content.ContextCompat
import javax.inject.Inject

class AutoStartBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onReceive(context: Context, intent: Intent) {

        Toast.makeText(context, "Rebooting Umweltify...", Toast.LENGTH_SHORT).show();
        val service = Intent(context, BatteryService::class.java)
        ContextCompat.startForegroundService(context, service);


    }

}