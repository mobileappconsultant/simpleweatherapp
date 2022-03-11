package com.android.openweather.domain.repository

import com.android.openweather.TestCoroutineRule
import com.android.openweather.data.network.WeatherApi
import com.android.openweather.data.network.model.City
import com.android.openweather.data.network.model.WeatherApiSchema
import com.android.openweather.data.network.model.WeatherSchema
import com.android.openweather.data.network.request.WeatherRequest
import com.android.openweather.domain.WeatherMapper
import com.android.openweather.domain.model.WeatherDomain
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherApiRepositoryImplTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var sut: WeatherApiRepository

    private val weatherMapper = mockk<WeatherMapper>()

    private val weatherApi = mockk<WeatherApi>()

    @Before
    fun setUp() {
        sut = WeatherApiRepositoryImpl(weatherApi = weatherApi, weatherMapper = weatherMapper)
    }

    @Test
    fun `given when getWeatherData is called and api call is successful, then it should return the correct data `() = runTest {
        val mockedCityName = "Here"

        val mockedCity = mockk<City>().apply {
            every { name } returns mockedCityName
        }

        val mockedWeather = mockk<WeatherSchema>()

        val mockedWeatherResponse = WeatherApiSchema(
            city = mockedCity,
            cnt = 1,
            cod = "",
            message = 0,
            list = listOf(
                mockedWeather,
                mockedWeather
            )
        )

        val mockedWeatherDomain = mockk<WeatherDomain>().apply {
            every { city } returns mockedCityName
        }
        coEvery {
            weatherApi.getWeatherForecast(any(), any())
        } returns mockedWeatherResponse

        every {
            weatherMapper.mapToDomain(any() as WeatherApiSchema)
        } returns mapOf(
            "12-02-02" to listOf(mockedWeatherDomain)
        )
        val weatherRequest = WeatherRequest(lat = 8.1, long = 12.0)

        val response = sut.getWeatherData(weatherRequest)
        Assert.assertEquals(mockedCityName, response.first().entries.first().value.first().city)
    }
}
