package com.android.openweather.data.network

import com.android.openweather.BuildConfig
import com.android.openweather.data.network.model.WeatherApiSchema
import com.android.openweather.utils.AppConstant
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("appId") appId: String = BuildConfig.apiKey,
        @Query("units") units: String = AppConstant.METRIC
    ): WeatherApiSchema
}
