package com.android.openweather.domain.usecases.network

import com.android.openweather.data.network.request.WeatherRequest
import com.android.openweather.domain.model.WeatherDomain
import com.android.openweather.domain.repository.WeatherApiRepository
import com.android.openweather.domain.usecases.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(val weatherApiRepository: WeatherApiRepository) : FlowUseCase<Map<String, List<WeatherDomain>>, WeatherRequest>() {
    override suspend fun buildFlowUseCase(params: WeatherRequest?): Flow<Map<String, List<WeatherDomain>>> =
        params?.let { weatherApiRepository.getWeatherData(it) } ?: flowOf(mapOf())
}
