package com.rahman.umweltify.persentation.util

object BatteryUtil {

    fun getBatteryCurrentNowInAmperes(value: Int): Int {

        if(android.os.Build.MANUFACTURER == "Xiaomi"){
            var value = if (value != 0 && value != Int.MIN_VALUE) value else 0
            return Math.round((value.toDouble() / 1000 * -1)).toInt()
        }else{
            return value;
        }

    }

}