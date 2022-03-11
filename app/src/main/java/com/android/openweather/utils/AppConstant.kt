package com.android.openweather.utils

import android.location.Location

object AppConstant {
    const val METRIC: String = "metric"
    const val DATABASE_NAME = "com.android.testApplication.db"
    val DEFAULT_LOCATION = Location("").apply {
        latitude = 52.77
        longitude = 51.76
    }
}
