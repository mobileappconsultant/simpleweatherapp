package com.android.openweather.domain.model

data class WeatherDomain(
    val city: String,
    val temperature: Double,
    val weather: String,
    val time: String,
    val date: String,
    val weatherImage: String
)
