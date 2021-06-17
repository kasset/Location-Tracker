package com.gmail.assetkikbayev.locationtracker.model.db

import androidx.room.*
import io.reactivex.rxjava3.core.Single

@Dao
interface LocationDao {
    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllLocations(): Single<List<Location>>

    @Delete
    fun delete(location: Location): Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(location: Location): Single<Long>

    companion object {
        const val TABLE_NAME = "LOCATION_TABLE"
    }
}