package com.android.openweather.domain.usecases

import com.android.openweather.TestCoroutineRule
import com.android.openweather.domain.repository.cache.WeatherCacheRepository
import com.android.openweather.domain.usecases.local.GetWeatherFromDbUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetWeatherFromDbUseCaseTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val weatherCacheRepository: WeatherCacheRepository = mockk<WeatherCacheRepository>(relaxed = true).apply {
        coEvery {
            getAllWeather()
        } returns flowOf()
    }

    private val sut: GetWeatherFromDbUseCase = GetWeatherFromDbUseCase(weatherCacheRepository)

    @Test
    fun `given when getWeatherFromDbUseCase is executed, verify that weatherCacheRepository getAllWeather is called `() = testCoroutineRule.runTest {
        sut.execute()
        coVerify {
            weatherCacheRepository.getAllWeather()
        }
    }
}
