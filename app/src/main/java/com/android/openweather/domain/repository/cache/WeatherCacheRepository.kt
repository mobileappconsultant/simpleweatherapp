package com.android.openweather.domain.repository.cache

import com.android.openweather.domain.model.WeatherDomain
import kotlinx.coroutines.flow.Flow

interface WeatherCacheRepository {
    fun getAllWeather(): Flow<Map<String, List<WeatherDomain>>>
    suspend fun insert(weather: List<WeatherDomain>)
    suspend fun deleteAll()
}
