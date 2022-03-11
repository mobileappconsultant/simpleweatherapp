package com.android.openweather.domain.usecases

import com.android.openweather.TestCoroutineRule
import com.android.openweather.data.network.request.WeatherRequest
import com.android.openweather.domain.repository.WeatherApiRepository
import com.android.openweather.domain.usecases.network.GetWeatherUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetWeatherFromApiUseCaseTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val weatherApiRepository: WeatherApiRepository = mockk<WeatherApiRepository>(relaxed = true).apply {
        coEvery {
            getWeatherData(any())
        } returns flowOf()
    }

    private val sut: GetWeatherUseCase = GetWeatherUseCase(weatherApiRepository)

    @Test
    fun `given when getWeatherUseCase is executed, verify that weatherRepository getWeatherData is called `() = testCoroutineRule.runTest {
        val mockedRequest = WeatherRequest(1.0, 2.0)
        sut.execute(mockedRequest)
        coVerify {
            weatherApiRepository.getWeatherData(mockedRequest)
        }
    }
}
