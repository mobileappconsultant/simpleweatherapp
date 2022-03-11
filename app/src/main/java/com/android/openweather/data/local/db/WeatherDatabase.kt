package com.android.openweather.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.openweather.data.local.dao.WeatherDao
import com.android.openweather.data.local.entity.WeatherEntity

@Database(
    entities = [WeatherEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao
}
