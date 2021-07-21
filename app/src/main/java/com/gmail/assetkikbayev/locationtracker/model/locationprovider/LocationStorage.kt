package com.gmail.assetkikbayev.locationtracker.model.locationprovider

import android.annotation.SuppressLint
import androidx.work.*
import com.gmail.assetkikbayev.locationtracker.model.db.Location
import com.gmail.assetkikbayev.locationtracker.model.db.LocationDao
import com.gmail.assetkikbayev.locationtracker.model.firebase.authentification.RemoteAuthSource
import com.gmail.assetkikbayev.locationtracker.model.firebase.firestore.RemoteDataSource
import com.gmail.assetkikbayev.locationtracker.model.workmanager.LocationUploadWorker
import com.gmail.assetkikbayev.locationtracker.utils.Constants
import com.gmail.assetkikbayev.locationtracker.utils.scheduleUploadLocationJob
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationStorage @Inject constructor(
    private val remoteServer: RemoteDataSource,
    private val localDB: LocationDao,
    private val locationProvider: UserLocationProvider,
    private val firebaseAuth: RemoteAuthSource,
    private val workManager: WorkManager
) {
    @SuppressLint("SimpleDateFormat")
    private val date = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

    fun saveLocation(): Completable = locationProvider.observeLocation()
        .subscribeOn(Schedulers.io())
        .flatMapCompletable { location ->
            val coordinates = Location(
                0,
                location.longitude,
                location.altitude,
                date.format(Date()),
                firebaseAuth.getCurrentUserId()!!
            )
            remoteServer.sendLocation(coordinates)
                .onErrorResumeNext {
                    if (it.message == Constants.SERVER_ERROR) {
                        return@onErrorResumeNext localDB.save(coordinates)
                            .andThen(Completable.fromAction {
                                workManager.scheduleUploadLocationJob(
                                    Constants.DEFERRABLE_JOB,
                                    LocationUploadWorker::class.java
                                )
                            })
                    } else {
                        return@onErrorResumeNext Completable.error(it)
                    }
                }
        }

    fun stopLocationUpdates(): Completable = locationProvider.stopLocationProvider()

    fun transferToRemoteServer(): Completable = localDB.getAllLocations()
        .subscribeOn(Schedulers.io())
        .flatMapCompletable { locations -> remoteServer.sendLocationsList(locations) }
}