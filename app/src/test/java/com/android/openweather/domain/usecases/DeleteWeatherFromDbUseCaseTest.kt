package com.android.openweather.domain.usecases

import com.android.openweather.TestCoroutineRule
import com.android.openweather.domain.repository.cache.WeatherCacheRepository
import com.android.openweather.domain.usecases.local.DeleteWeatherFromDbUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DeleteWeatherFromDbUseCaseTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val weatherCacheRepository: WeatherCacheRepository = mockk<WeatherCacheRepository>(relaxed = true).apply {
        coEvery {
            deleteAll()
        } returns Unit
    }

    private val sut: DeleteWeatherFromDbUseCase =
        DeleteWeatherFromDbUseCase(weatherCacheRepository)

    @Test
    fun `given when getWeatherFromDbUseCase is executed, verify that weatherCacheRepository deleteAll is called `() = testCoroutineRule.runTest {
        sut.execute()
        coVerify {
            weatherCacheRepository.deleteAll()
        }
    }
}
