package com.udacity.asteroidradar.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.getDbModel
import com.udacity.asteroidradar.database.DatabaseImage
import com.udacity.asteroidradar.database.ImageDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageRepository(private val imageDao: ImageDao) {

    val photoOfTheDay: LiveData<DatabaseImage> = imageDao.getImageOfTheDay()

    suspend fun getPhotoOfTheDay(sharePref: SharedPreferences, startDate: String, apiKey: String) {
        withContext(Dispatchers.IO) {
            val networkImage = Network.nasaApi.getNasaImageOfTheDay(apiKey)
            // clear the current images
            imageDao.deleteAll()
            imageDao.insertAll(networkImage.getDbModel())
            with(sharePref.edit()) {
                putString(Constants.SYNC_DATE, startDate)
                apply()
            }


        }
    }
}