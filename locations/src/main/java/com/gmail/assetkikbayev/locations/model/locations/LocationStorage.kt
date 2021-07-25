package com.gmail.assetkikbayev.locations.model.locations

import android.annotation.SuppressLint
import com.gmail.assetkikbayev.locations.model.data.RemoteDataSource
import io.reactivex.Flowable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationStorage @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    @SuppressLint("SimpleDateFormat")
    fun getUserLocation(dayOfMonth: Int, month: Int, year: Int): Flowable<Map<String, Any>> {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        var calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        return remoteDataSource.getLocation(dateFormat.format(calendar.time))
            .flatMapIterable { documents -> documents }
            .map { location -> location.data }
    }
}
