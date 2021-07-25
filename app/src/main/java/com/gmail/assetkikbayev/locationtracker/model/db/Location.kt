package com.gmail.assetkikbayev.locationtracker.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LOCATION_TABLE")
data class Location(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val longitude: Double,
    var latitude: Double,
    val date: String,
    val time: String,
    val userId: String,
)
