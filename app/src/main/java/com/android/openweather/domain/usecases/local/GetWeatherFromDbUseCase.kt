package com.android.openweather.domain.usecases.local

import com.android.openweather.domain.model.WeatherDomain
import com.android.openweather.domain.repository.cache.WeatherCacheRepository
import com.android.openweather.domain.usecases.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherFromDbUseCase @Inject constructor(private val weatherCacheRepository: WeatherCacheRepository) : FlowUseCase<Map<String, List<WeatherDomain>>, Unit>() {
    override suspend fun buildFlowUseCase(params: Unit?): Flow<Map<String, List<WeatherDomain>>> {
        return weatherCacheRepository.getAllWeather()
    }
}
