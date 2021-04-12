package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidDao {
    @Query("select * from databaseasteroid order by strftime('%Y-%d-%m-%Y', closeApproachDate) asc")
    fun getAsteroids(): LiveData<List<DatabaseAsteroid>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: DatabaseAsteroid)

    @Query("delete from databaseasteroid")
    fun deleteAll()


}