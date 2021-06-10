package com.gmail.assetkikbayev.locationtracker.di.modules

import androidx.room.Room
import com.gmail.assetkikbayev.locationtracker.di.App
import com.gmail.assetkikbayev.locationtracker.model.db.LocationDao
import com.gmail.assetkikbayev.locationtracker.model.db.LocationTrackerDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideLocationDB(app: App) = Room.databaseBuilder(
        app,
        LocationTrackerDB::class.java,
        "LocationTracker_database"
    ).fallbackToDestructiveMigration()
        .build()


    @Provides
    @Singleton
    fun provideLocationDao(db: LocationTrackerDB): LocationDao = db.locationDao()
}