package com.android.openweather.domain.repository

import com.android.openweather.data.network.request.WeatherRequest
import com.android.openweather.domain.model.WeatherDomain
import kotlinx.coroutines.flow.Flow

interface WeatherApiRepository {
    suspend fun getWeatherData(weatherRequest: WeatherRequest): Flow<Map<String, List<WeatherDomain>>>
}
