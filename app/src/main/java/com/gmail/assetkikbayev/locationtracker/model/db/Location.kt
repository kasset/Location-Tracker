package com.gmail.assetkikbayev.locationtracker.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Location_table")
data class Location(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val longitude: Double,
    var latitude: Double,
    val timestamp: String,
    val userId: String
)
