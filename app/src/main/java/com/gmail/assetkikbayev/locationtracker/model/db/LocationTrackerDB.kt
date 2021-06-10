package com.gmail.assetkikbayev.locationtracker.model.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Location::class], version = 1, exportSchema = false)
abstract class LocationTrackerDB: RoomDatabase() {
    abstract fun locationDao(): LocationDao
}