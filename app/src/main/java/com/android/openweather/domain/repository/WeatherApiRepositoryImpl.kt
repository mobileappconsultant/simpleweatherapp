package com.android.openweather.domain.repository

import com.android.openweather.data.network.WeatherApi
import com.android.openweather.data.network.request.WeatherRequest
import com.android.openweather.domain.WeatherMapper
import com.android.openweather.domain.model.WeatherDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class WeatherApiRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherMapper: WeatherMapper
) : WeatherApiRepository {
    override suspend fun getWeatherData(weatherRequest: WeatherRequest): Flow<Map<String, List<WeatherDomain>>> =
        flowOf(weatherMapper.mapToDomain(weatherApi.getWeatherForecast(weatherRequest.lat, weatherRequest.long)))
}
