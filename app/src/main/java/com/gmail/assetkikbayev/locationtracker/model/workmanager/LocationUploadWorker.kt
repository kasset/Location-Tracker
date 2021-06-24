package com.gmail.assetkikbayev.locationtracker.model.workmanager

import android.content.Context
import android.location.Location
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.gmail.assetkikbayev.locationtracker.model.db.LocationDao
import com.gmail.assetkikbayev.locationtracker.model.firebase.firestore.RemoteDataSource
import io.reactivex.Single
import javax.inject.Inject

class LocationUploadWorker @Inject constructor(
    context: Context,
    workerParams: WorkerParameters,
    private val remoteServer: RemoteDataSource,
    private val locationDao: LocationDao
) : RxWorker(context, workerParams) {
    override fun createWork(): Single<Result> {
//        locationDao.getAllLocations()
//            .subscribe { location ->
//                location.forEach { location ->
//                val location = Location.CREATOR
//
//                }
//            }
        TODO("Not yet implemented")
    }
}