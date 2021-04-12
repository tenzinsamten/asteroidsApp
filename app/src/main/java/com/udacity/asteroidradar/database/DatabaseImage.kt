package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseImage(
        @PrimaryKey
        val url: String,
        val title: String,
        val mediaType: String
)