package com.android.openweather.domain.usecases.local

import com.android.openweather.domain.repository.cache.WeatherCacheRepository
import com.android.openweather.domain.usecases.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class DeleteWeatherFromDbUseCase @Inject constructor(private val weatherCacheRepository: WeatherCacheRepository) : FlowUseCase<Unit, Unit>() {
    override suspend fun buildFlowUseCase(params: Unit?): Flow<Unit> {
        return flowOf(weatherCacheRepository.deleteAll())
    }
}
