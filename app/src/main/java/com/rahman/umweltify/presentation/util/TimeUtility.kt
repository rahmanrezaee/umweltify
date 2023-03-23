package com.rahman.umweltify.presentation.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object TimeUtility {

    @SuppressLint("SimpleDateFormat")
    fun convertLongToOnlyTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("hh:mm aaa")
        return format.format(date)
    }

    fun betweenTwoDate(startDate: Long, endDate: Long): String {
        val diffInHours = TimeUnit.MILLISECONDS.toHours(endDate - startDate)
        val diffInSec: Long = TimeUnit.MILLISECONDS.toSeconds(endDate - startDate)
        val diffInMin: Long = TimeUnit.MILLISECONDS.toMinutes(endDate - startDate)

        val hourString: String = if (diffInHours > 0) ("$diffInHours Hour ") else ""

        val minString: String =
            if (diffInMin > 0) ("${if (diffInMin > 59) (diffInMin % 60) else diffInMin} Min ") else ""
        val secString: String =
            if (diffInSec > 0) ("${if (diffInSec > 59) (diffInSec % 60) else diffInSec} Sec") else ""

        if (hourString.isEmpty()) {
            return "$minString$secString";
        } else {
            return "$hourString$minString"
        }
    }


    @SuppressLint("SimpleDateFormat")
    fun convertUTC(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        return format.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun getMonth(time: String): String {

        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = formatter.parse(time.split("T").first())
        val format = SimpleDateFormat("MMM")
        return format.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun getMonthNumber(date: String): Float {

        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = formatter.parse(date.split("T").first())
        val format = SimpleDateFormat("MM")
        return format.format(date).toFloat()

    }
}