package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.PictureOfDay
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.SplittableRandom

//private const val BASE_URL = "https://api.nasa.gov/planetary/apod?api_key=8SOGeWqFgISMgEbT1hAqRs9D2RRNVjmhkPaBLnjk"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface NasaApiService{
    //@GET("neo/rest/v1/feed?start_date=2023-01-15&end_date=2023-01-22&api_key=8SOGeWqFgISMgEbT1hAqRs9D2RRNVjmhkPaBLnjk")
    @GET("neo/rest/v1/feed")
    suspend fun getProperties(@Query("start_date") start_date: String, @Query("api_key") api_key: String): String

    //@GET("https://api.nasa.gov/planetary/apod?api_key=8SOGeWqFgISMgEbT1hAqRs9D2RRNVjmhkPaBLnjk")
    @GET("https://api.nasa.gov/planetary/apod")
    suspend fun getImageOfTheDay(@Query("api_key") api_key: String): String
}

object NasaApi{
    val retrofitService: NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }
}