package com.gmail.assetkikbayev.locationtracker.model.locationprovider

import androidx.work.*
import com.gmail.assetkikbayev.locationtracker.model.db.Location
import com.gmail.assetkikbayev.locationtracker.model.db.LocationDao
import com.gmail.assetkikbayev.locationtracker.model.firebase.authentification.RemoteAuthSource
import com.gmail.assetkikbayev.locationtracker.model.firebase.firestore.RemoteDataSource
import com.gmail.assetkikbayev.locationtracker.model.workmanager.LocationUploadWorker
import com.gmail.assetkikbayev.locationtracker.utils.Constants
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
    private val workManager: WorkManager,
) {

    fun saveLocation(): Completable = locationProvider.observeLocation()
        .subscribeOn(Schedulers.io())
        .flatMapCompletable { location ->
            val coordinates = Location(
                0,
                location.longitude,
                location.altitude,
                SimpleDateFormat.getDateTimeInstance().format(Date()),
                firebaseAuth.getCurrentUserId()!!
            )
            remoteServer.sendLocation(coordinates)
                .onErrorResumeNext {
                    if (it.message == Constants.SERVER_ERROR) {
                        if (!isWorkScheduled(Constants.DEFERRABLE_JOB)) {
                            workManager.cancelAllWorkByTag(Constants.DEFERRABLE_JOB)
                            val constraints =
                                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
                                    .build()
                            val oneTimeRequest =
                                OneTimeWorkRequest.Builder(LocationUploadWorker::class.java)
                                    .setConstraints(constraints)
                                    .addTag(Constants.DEFERRABLE_JOB)
                                    .build()
                            workManager.enqueue(oneTimeRequest)
                        }
                        return@onErrorResumeNext localDB.save(coordinates)
                    } else {
                        return@onErrorResumeNext Completable.error(it)
                    }
                }
        }

    fun stopLocationUpdates(): Completable = locationProvider.stopLocationProvider()

    fun transferToRemoteServer(): Completable = localDB.getAllLocations()
        .subscribeOn(Schedulers.io())
        .flatMapCompletable { locations -> remoteServer.sendLocationsList(locations) }

    private fun isWorkScheduled(tag: String): Boolean {
        val workList = workManager.getWorkInfosByTag(tag).get()
        workList.forEach { workInfo ->
            if (workInfo.state == WorkInfo.State.RUNNING) {
                return true
            }
        }
        return false
    }
}