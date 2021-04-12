package com.udacity.asteroidradar.api

import com.squareup.moshi.Json
import com.udacity.asteroidradar.database.DatabaseImage

data class NetworkImage(
    val title: String,
    @Json(name = "media_type")
    val mediaType: String?,
    val url: String
)

fun NetworkImage.getDbModel(): DatabaseImage {
    return DatabaseImage(
        title = this.title,
        mediaType = this.mediaType ?: "",
        url = this.url
    )
}