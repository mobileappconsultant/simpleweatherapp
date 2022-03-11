package com.android.openweather.di

import android.content.Context
import androidx.room.Room
import com.android.openweather.data.local.dao.WeatherDao
import com.android.openweather.data.local.db.WeatherDatabase
import com.android.openweather.utils.AppConstant.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java, DATABASE_NAME
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao {
        return weatherDatabase.getWeatherDao()
    }
}
