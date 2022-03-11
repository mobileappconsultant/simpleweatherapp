package com.android.openweather.ui.viewmodel

import android.location.Location
import com.android.openweather.TestCoroutineRule
import com.android.openweather.data.network.request.WeatherRequest
import com.android.openweather.domain.WeatherMapper
import com.android.openweather.domain.usecases.local.DeleteWeatherFromDbUseCase
import com.android.openweather.domain.usecases.local.GetWeatherFromDbUseCase
import com.android.openweather.domain.usecases.local.InsertWeatherToDbUseCase
import com.android.openweather.domain.usecases.network.GetWeatherUseCase
import com.android.openweather.ui.WeatherViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var sut: WeatherViewModel
    private val getWeatherFromDbUseCase = mockk<GetWeatherFromDbUseCase>().apply {
        coEvery { execute() } returns flowOf()
    }
    private val insertWeatherToDbUseCase = mockk<InsertWeatherToDbUseCase>()
    private val getWeatherUseCase = mockk<GetWeatherUseCase>()
    private val deleteWeatherFromDbUseCase = mockk<DeleteWeatherFromDbUseCase>().apply {
        coEvery { execute() } returns flowOf()
    }
    private val weatherMapper = WeatherMapper()
    @Before
    fun setUp() {
        mockkClass(Location::class).apply {
            every { latitude } returns 1.0
            every { longitude } returns 1.0
        }
        sut = WeatherViewModel(
            weatherMapper = weatherMapper,
            getWeatherFromDbUseCase = getWeatherFromDbUseCase,
            getWeatherUseCase = getWeatherUseCase,
            deleteWeatherFromDbUseCase = deleteWeatherFromDbUseCase,
            insertWeatherToDbUseCase = insertWeatherToDbUseCase
        )
    }

    @Test
    fun `given when user location is available and user request for weather data, the getWeatherUsecase  should be called`() =
        testCoroutineRule.runTest {
            coEvery {
                getWeatherUseCase.execute(any())
            } returns flowOf(mapOf())
            sut.getWeatherDetails(WeatherRequest(10.0, 9.1))
            coVerify {
                getWeatherUseCase.execute(any())
            }
            coVerify {
                insertWeatherToDbUseCase.execute(any())
            }
        }

    @Test
    fun `given when user location is available and user request for weather data, the data is available, deleteWeatherFromDbUseCase and insertWeatherToDbUseCase  should be called`() =
        testCoroutineRule.runTest {
            coEvery {
                getWeatherUseCase.execute(any())
            } returns flowOf(mapOf())
            sut.getWeatherDetails(WeatherRequest(10.0, 9.1))

            coVerify {
                deleteWeatherFromDbUseCase.execute()
            }
            coVerify {
                insertWeatherToDbUseCase.execute(any())
            }
        }
}
