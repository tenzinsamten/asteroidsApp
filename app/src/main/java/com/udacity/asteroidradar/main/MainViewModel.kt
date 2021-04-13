package com.udacity.asteroidradar.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import com.udacity.asteroidradar.repository.ImageRepository
import com.udacity.asteroidradar.util.GeneralHelper
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val asteroidsRepository = AsteroidsRepository(database.asteroidDao)
    private val imageRepository = ImageRepository(database.imageDao)

    init {
        viewModelScope.launch {
            val startDate = GeneralHelper().getCurrentDateInString()
            val sharePref =
                application.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE)
            val lastSyncDate = sharePref.getString(Constants.SYNC_DATE,null)
            if(lastSyncDate == null || lastSyncDate != startDate){
                asteroidsRepository.getAsteroids()
                imageRepository.getPhotoOfTheDay(sharePref,startDate,BuildConfig.NASA_API_KEY)
            }
        }
    }

    val asteroidList = asteroidsRepository.asteroids
    val photoOfTheDay = imageRepository.photoOfTheDay
}