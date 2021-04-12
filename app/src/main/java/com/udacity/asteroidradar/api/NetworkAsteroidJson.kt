package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.database.DatabaseAsteroid

data class NetworkAsteroidJson(

    val elementCount: Int,
    val nearEarthObjects: List<NetworkAsteroid>
)

/**
 * Convert Network results to database objects
 */
fun NetworkAsteroidJson.asDomainModel(): Array<DatabaseAsteroid> {
    return nearEarthObjects.map {
        DatabaseAsteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}