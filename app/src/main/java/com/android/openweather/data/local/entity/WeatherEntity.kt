package com.android.openweather.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val city: String,
    val temperature: Double,
    val weather: String,
    val time: String,
    val date: String,
    val weatherImage: String
)
