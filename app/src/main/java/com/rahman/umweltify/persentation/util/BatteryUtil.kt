package com.rahman.umweltify.persentation.util

import android.util.Log

object BatteryUtil {

    fun getBatteryCurrentNowInAmperes(value: Int): Int {

        Log.i("getBatteryCurrentNowInAmperes","getBatteryCurrentNowInAmperes $value")
        if(android.os.Build.MANUFACTURER == "Xiaomi"){
            var value = if (value != 0 && value != Int.MIN_VALUE) value else 0
            return Math.round((value.toDouble() / 1000 * -1)).toInt()
        }else{
            return value;

        }

    }

    fun getBatteryCurrentNowInWatt(currentNow: Int, voltage: Int): Double {


        var currentAmpere = getBatteryCurrentNowInAmperes(currentNow);
        return String.format(
            "%.1f",
            currentAmpere.times(voltage).div(1000000.0)
        ).toDouble()


    }
}