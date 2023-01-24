package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.asDB
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.await

enum class AsteroidFilters {
    TODAY, WEEK, ALL
}

class AsteroidRepository (private val database: AsteroidDatabase){

    var asteroidFilter = MutableLiveData<AsteroidFilters>().apply { postValue(AsteroidFilters.ALL) }

    val asteroids: LiveData<List<Asteroid>> = Transformations.map(Transformations.switchMap(asteroidFilter){ filter ->
        when(filter) {
            AsteroidFilters.TODAY -> database.asteroidDatabaseDao.getWeekAsteroids(getToday())
            AsteroidFilters.WEEK -> database.asteroidDatabaseDao.getWeekAsteroids(
                getNextSevenDaysFormattedDates()[6]
            )
            else -> database.asteroidDatabaseDao.getAllAsteroids()
        }
    }){
        it.asDomainModel()
    }

    private var _pictureOfTheDay = MutableLiveData<PictureOfDay>()
    val pictureOfTheDay: LiveData<PictureOfDay>
    get() = _pictureOfTheDay

    suspend fun refreshAsteroids(){
        withContext(Dispatchers.IO){
            try{
                val result = NasaApi.retrofitService.getProperties(getToday(), API_KEY)
                val asteroidList = parseAsteroidsJsonResult(JSONObject(result))
                database.asteroidDatabaseDao.clear()
                database.asteroidDatabaseDao.insertAll(*asteroidList.asDB())
                Log.i("AsteroidRepository", "Fetching asteroids: DONE")
            }catch (e: Exception){
                Log.i("AsteroidRepository", "Fetching asteroids: NOT DONE -> no internet connection")
            }
        }
    }

    suspend fun refreshImageOfTheDay(){
        withContext(Dispatchers.IO){
            try{
                val image = NasaApi.retrofitService.getImageOfTheDay(API_KEY)
                _pictureOfTheDay.postValue(parseImageOfTheDayJsonResult(JSONObject(image)))
                Log.i("AsteroidRepository", "Fetching image of the day: DONE")
            }catch (e: Exception){
                Log.i("AsteroidRepository", "Fetching image of the day: NOT DONE -> no internet connection")
            }
        }
    }

    fun updateFilter(filter: AsteroidFilters){
        asteroidFilter.value = filter
    }
}
