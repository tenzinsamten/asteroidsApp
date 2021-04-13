package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.database.DatabaseImage

data class NetworkImage(
    val title: String,
    val media_type: String?,
    val url: String
)

fun NetworkImage.getDbModel(): DatabaseImage {
    return DatabaseImage(
        title = this.title,
        mediaType = this.media_type ?: "",
        url = this.url
    )
}