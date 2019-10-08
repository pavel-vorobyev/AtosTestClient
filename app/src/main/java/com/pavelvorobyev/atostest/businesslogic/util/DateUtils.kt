package com.pavelvorobyev.atostest.businesslogic.util

import android.text.format.DateFormat
import java.util.*

object DateUtils {

    fun getDateFromTimestamp(timestamp: Int): String {
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.timeInMillis = timestamp.toLong() * 1000
        calendar.timeZone = TimeZone.getDefault()
        return DateFormat.format("dd.MM.yyyy", calendar).toString()
    }

    fun getTimeFromTimestamp(timestamp: Int): String {
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.timeInMillis = timestamp.toLong() * 1000
        calendar.timeZone = TimeZone.getDefault()
        return DateFormat.format("H:mm", calendar).toString()
    }

}