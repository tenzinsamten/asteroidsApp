package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageDao {

    @Query("select * from databaseimage")
    fun getImageOfTheDay(): LiveData<DatabaseImage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg image: DatabaseImage)

    @Query("delete from databaseimage")
    fun deleteAll()

}

