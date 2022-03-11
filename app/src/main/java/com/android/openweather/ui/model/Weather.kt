package com.android.openweather.ui.model

data class Weather(
    val city: String,
    val temperature: Double,
    val weather: String,
    val time: String,
    val date: String,
    val weatherImage: String
)
