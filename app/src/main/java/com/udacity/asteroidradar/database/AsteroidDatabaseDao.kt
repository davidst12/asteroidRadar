package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDatabaseDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroid: AsteroidEntity)

    @Update
    fun update(asteroid: AsteroidEntity)

    @Query("SELECT * from nasa_asteroid_table WHERE id = :key")
    fun get(key: Long): AsteroidEntity

    @Query("DELETE FROM nasa_asteroid_table")
    fun clear()

    @Query("SELECT * FROM nasa_asteroid_table ORDER BY close_approach_date ASC")
    fun getAllAsteroids(): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM nasa_asteroid_table WHERE close_approach_date = :today")
    fun getTodayAsteroids(today: String): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM nasa_asteroid_table WHERE close_approach_date <= :lastDayWeek ORDER BY close_approach_date ASC")
    fun getWeekAsteroids(lastDayWeek: String): LiveData<List<AsteroidEntity>>

}