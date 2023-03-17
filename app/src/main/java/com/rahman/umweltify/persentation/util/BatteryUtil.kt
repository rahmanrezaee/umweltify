package com.rahman.umweltify.persentation.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager


object BatteryUtil {

    fun getBatteryCurrentNowInAmperes(value: Int): Int {

        if(android.os.Build.MANUFACTURER == "Xiaomi"){
            var value = if (value != 0 && value != Int.MIN_VALUE) value else 0
            return Math.round((value.toDouble() / 1000 * -1)).toInt()
        }else{
            return value;
        }

    }

    fun getBatteryCurrentNowInWatt(currentNow: Int, voltage: Int): Double {
        var currentAmpere = getBatteryCurrentNowInAmperes(currentNow);
        return currentAmpere.toDouble().div(1000) * voltage.toDouble().div(1000);
    }

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String? {
        val deviceId: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        } else {
            val mTelephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (mTelephony.deviceId != null) {
                mTelephony.deviceId
            } else {
                Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            }
        }
        return deviceId
    }
}