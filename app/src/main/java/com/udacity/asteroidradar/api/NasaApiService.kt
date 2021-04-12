package com.udacity.asteroidradar.api

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApiService {

    @GET("neo/rest/v1/feed")
    fun getNearByAsteroids(@Query("start_date") startDate: String, @Query("end_date") endDate: String, @Query("api_key") apiKey: String): Deferred<String>


    @GET("/planetary/apod")
    suspend fun getNasaImageOfTheDay(@Query("api_key") apiKey: String): NetworkImage
}