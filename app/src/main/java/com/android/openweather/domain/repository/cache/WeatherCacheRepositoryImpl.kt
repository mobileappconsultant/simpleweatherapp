package com.android.openweather.domain.repository.cache

import com.android.openweather.data.local.dao.WeatherDao
import com.android.openweather.domain.WeatherMapper
import com.android.openweather.domain.model.WeatherDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherCacheRepositoryImpl @Inject constructor(private val weatherDao: WeatherDao, private val weatherMapper: WeatherMapper) : WeatherCacheRepository {
    override fun getAllWeather(): Flow<Map<String, List<WeatherDomain>>> {
        return weatherDao.getAll().map {
            it.map {
                weatherMapper.mapToDomain(it)
            }.groupBy { it.date }
        }
    }

    override suspend fun insert(weather: List<WeatherDomain>) {
        weather.forEach {
            weatherDao.insert(weatherMapper.mapToEntity(it))
        }
    }

    override suspend fun deleteAll() {
        weatherDao.deleteAll()
    }
}
