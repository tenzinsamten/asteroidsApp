package com.udacity.asteroidradar.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.database.DatabaseImage
import com.udacity.asteroidradar.database.ImageDao
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.domain.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class ImageRepository(private val imageDao: ImageDao) {

    val photoOfTheDay: LiveData<DatabaseImage> = imageDao.getImageOfTheDay()

    suspend fun getPhotoOfTheDay(sharePref : SharedPreferences,startDate : String) {
        withContext(Dispatchers.IO) {
            val networkImage = Network.nasaApi.getNasaImageOfTheDay(Constants.API_KEY)
            // clear the current image
            imageDao.deleteAll()
            imageDao.insertAll(networkImage.getDbModel())
            with(sharePref.edit()) {
                putString(Constants.SYNC_DATE, startDate)
                apply()
            }


        }
    }
}