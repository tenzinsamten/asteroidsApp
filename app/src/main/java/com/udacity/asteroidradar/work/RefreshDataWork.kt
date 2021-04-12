package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Constants.SYNC_DATE
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import com.udacity.asteroidradar.repository.ImageRepository
import com.udacity.asteroidradar.util.GeneralHelper
import retrofit2.HttpException

class RefreshDataWork(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWork"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val asteroidsRepository = AsteroidsRepository(database.asteroidDao)
        val imageRepository = ImageRepository(database.imageDao)
        val startDate = GeneralHelper().getCurrentDateInString()
        return try {
            val sharePref =
                applicationContext.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE)
            asteroidsRepository.getAsteroids()
            imageRepository.getPhotoOfTheDay(sharePref,startDate)
            Result.success()
        } catch (exception: HttpException) {
            Result.retry()
        }
    }
}