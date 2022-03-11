package com.android.openweather.data.network.model

import com.google.gson.annotations.SerializedName

data class WeatherApiSchema(
    @SerializedName("city")
    val city: City,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: List<WeatherSchema>,
    @SerializedName("message")
    val message: Int
)
