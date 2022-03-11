package com.android.openweather.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateUtils {
    private const val YYYY_MM_DD = "yyyy-MM-dd"

    private const val HH_MM = "HH:mm"

    private fun Long.formatDateString(format: String): String {
        val formatter = SimpleDateFormat(format, Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this
        return formatter.format(this)
    }

    fun Long.formatMillisToDateString(): String {
        return formatDateString(YYYY_MM_DD)
    }

    fun Long.formatMillisToTimeString(): String {
        return formatDateString(HH_MM)
    }
}
