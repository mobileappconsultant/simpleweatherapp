package com.android.openweather.di

import com.android.openweather.data.local.dao.WeatherDao
import com.android.openweather.data.network.WeatherApi
import com.android.openweather.domain.WeatherMapper
import com.android.openweather.domain.repository.WeatherApiRepository
import com.android.openweather.domain.repository.WeatherApiRepositoryImpl
import com.android.openweather.domain.repository.cache.WeatherCacheRepository
import com.android.openweather.domain.repository.cache.WeatherCacheRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideWeatherApiRepository(
        weatherApi: WeatherApi,
        weatherMapper: WeatherMapper
    ): WeatherApiRepository = WeatherApiRepositoryImpl(weatherApi, weatherMapper)

    @Provides
    fun provideWeatherCacheRepository(
        weatherDao: WeatherDao,
        weatherMapper: WeatherMapper
    ): WeatherCacheRepository = WeatherCacheRepositoryImpl(weatherDao, weatherMapper)
}
