package com.android.openweather.domain.usecases

import com.android.openweather.TestCoroutineRule
import com.android.openweather.domain.repository.cache.WeatherCacheRepository
import com.android.openweather.domain.usecases.local.InsertWeatherToDbUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class InsertWeatherToDbUseCaseTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val weatherCacheRepository: WeatherCacheRepository = mockk<WeatherCacheRepository>(relaxed = true).apply {
        coEvery {
            insert(any())
        } returns Unit
    }

    private val sut: InsertWeatherToDbUseCase =
        InsertWeatherToDbUseCase(weatherCacheRepository)

    @Test
    fun `given when getWeatherFromDbUseCase is executed, verify that weatherCacheRepository insert is called `() = testCoroutineRule.runTest {
        sut.execute(listOf())
        coVerify {
            weatherCacheRepository.insert(any())
        }
    }
}
