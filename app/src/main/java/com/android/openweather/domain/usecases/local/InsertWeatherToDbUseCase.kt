package com.android.openweather.domain.usecases.local

import com.android.openweather.domain.model.WeatherDomain
import com.android.openweather.domain.repository.cache.WeatherCacheRepository
import com.android.openweather.domain.usecases.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class InsertWeatherToDbUseCase @Inject constructor(private val weatherCacheRepository: WeatherCacheRepository) : FlowUseCase<Unit, List<WeatherDomain>>() {
    override suspend fun buildFlowUseCase(params: List<WeatherDomain>?): Flow<Unit> {
        return flowOf(params?.let { weatherCacheRepository.insert(it) } ?: Unit)
    }
}
