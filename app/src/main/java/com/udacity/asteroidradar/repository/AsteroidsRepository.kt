package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.NetworkAsteroidJson
import com.udacity.asteroidradar.api.asDomainModel
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.util.GeneralHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidsRepository(private val databaseDao: AsteroidDao) {

    val asteroids: LiveData<List<Asteroid>> = Transformations.map(databaseDao.getAsteroids()) {
        it.asDomainModel()
    }

    suspend fun getAsteroids() {
        withContext(Dispatchers.IO) {
            val startDate = GeneralHelper().getCurrentDateInString()
            val endDate = GeneralHelper().getEndDateInString()
            val networkAsteroidString =
                Network.nasaApi.getNearByAsteroids(startDate, endDate, Constants.API_KEY).await()
            val jsonObject = JSONObject(networkAsteroidString)
            val asteroidList = parseAsteroidsJsonResult(jsonObject)
            val networkAsteroidJson = NetworkAsteroidJson(asteroidList.size, asteroidList)
            // clear the current content in the database
            databaseDao.deleteAll()
            databaseDao.insertAll(*networkAsteroidJson.asDomainModel())
        }
    }
}