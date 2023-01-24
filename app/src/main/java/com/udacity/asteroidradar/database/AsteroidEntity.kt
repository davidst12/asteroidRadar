package com.udacity.asteroidradar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Asteroid

@Entity(tableName = "nasa_asteroid_table")
data class AsteroidEntity(
    @PrimaryKey
    val id: Long = 0L,

    @ColumnInfo(name = "code_name")
    val codename: String = "",

    @ColumnInfo(name = "close_approach_date")
    val closeApproachDate: String= "",

    @ColumnInfo(name = "absolute_magnitude")
    val absoluteMagnitude: Double= 0.0,

    @ColumnInfo(name = "estimated_diameter")
    val estimatedDiameter: Double= 0.0,

    @ColumnInfo(name = "relative_velocity")
    val relativeVelocity: Double= 0.0,

    @ColumnInfo(name = "distance_from_earth")
    val distanceFromEarth: Double= 0.0,

    @ColumnInfo(name = "potentially_hazardous")
    val isPotentiallyHazardous: Boolean = false
)

fun List<AsteroidEntity>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid (
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous)
    }
}