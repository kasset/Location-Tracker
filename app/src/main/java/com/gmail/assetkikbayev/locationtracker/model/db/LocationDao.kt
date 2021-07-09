package com.gmail.assetkikbayev.locationtracker.model.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single


@Dao
interface LocationDao {
    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllLocations(): Single<List<Location>>

    @Delete
    fun delete(location: Location): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(location: Location): Completable

    companion object {
        const val TABLE_NAME = "LOCATION_TABLE"
    }
}