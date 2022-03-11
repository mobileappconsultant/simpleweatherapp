package com.android.openweather.domain

import com.android.openweather.data.local.entity.WeatherEntity
import com.android.openweather.data.network.model.City
import com.android.openweather.data.network.model.WeatherApiSchema
import com.android.openweather.data.network.model.WeatherSchema
import com.android.openweather.domain.model.WeatherDomain
import com.android.openweather.ui.model.Weather
import com.android.openweather.utils.DateUtils.formatMillisToDateString
import com.android.openweather.utils.DateUtils.formatMillisToTimeString
import javax.inject.Inject

class WeatherMapper @Inject constructor() {
    fun mapToDomain(schema: WeatherApiSchema): Map<String, List<WeatherDomain>> = schema.list.map { mapToDomain(schema.city, it) }.groupBy { it.date }

    private fun mapToDomain(city: City, weatherSchema: WeatherSchema): WeatherDomain = WeatherDomain(
        city = city.name,
        time = weatherSchema.dt.toLong().times(MILLIS_IN_MIN).formatMillisToTimeString(),
        temperature = weatherSchema.main.temp,
        weather = weatherSchema.weather.first().main,
        date = weatherSchema.dt.toLong().times(MILLIS_IN_MIN).formatMillisToDateString(),
        weatherImage = weatherSchema.weather.first().icon
    )

    private fun mapToPresentation(domain: WeatherDomain) = Weather(
        city = domain.city,
        temperature = domain.temperature,
        time = domain.time,
        date = domain.date,
        weather = domain.weather,
        weatherImage = domain.weatherImage
    )

    fun mapToDomain(entity: WeatherEntity) = WeatherDomain(
        city = entity.city,
        temperature = entity.temperature,
        time = entity.time,
        date = entity.date,
        weather = entity.weather,
        weatherImage = entity.weatherImage
    )

    fun mapToEntity(entity: WeatherDomain) = WeatherEntity(
        city = entity.city,
        temperature = entity.temperature,
        time = entity.time,
        date = entity.date,
        weather = entity.weather,
        weatherImage = entity.weatherImage
    )

    fun mapToWeatherList(weatherList: Map<String, List<WeatherDomain>>): List<List<Weather>> {
        return weatherList.map {
            it.value.map { domain ->
                mapToPresentation(domain)
            }
        }
    }

    companion object {
        private const val MILLIS_IN_MIN = 1000L
    }
}
