package com.android.openweather.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.openweather.data.local.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(weatherEntity: WeatherEntity): Long

    @Query("SELECT * FROM weather")
    fun getAll(): Flow<List<WeatherEntity>>

    @Query("DELETE FROM weather")
    suspend fun deleteAll()
}
