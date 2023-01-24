package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _navigateToDetailFragment = MutableLiveData<Asteroid?>()
    val navigateToDetailFragment: LiveData<Asteroid?>
        get() = _navigateToDetailFragment


    private val database = getDatabase(application)
    val asteroidRepository = AsteroidRepository(database)

    init {
        viewModelScope.launch {

            asteroidRepository.refreshAsteroids()
            asteroidRepository.refreshImageOfTheDay()
        }
    }

    val asteroidList = asteroidRepository.asteroids

    fun onAsteroidClicked(asteroid: Asteroid){
        _navigateToDetailFragment.value = asteroid
    }
    fun onDetailNavigate(){
        _navigateToDetailFragment.value = null
    }
}
