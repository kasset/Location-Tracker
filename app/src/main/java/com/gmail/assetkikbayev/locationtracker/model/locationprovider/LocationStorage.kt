package com.gmail.assetkikbayev.locationtracker.model.locationprovider

import com.gmail.assetkikbayev.locationtracker.model.db.Location
import com.gmail.assetkikbayev.locationtracker.model.db.LocationDao
import com.gmail.assetkikbayev.locationtracker.model.firebase.authentification.RemoteAuthSource
import com.gmail.assetkikbayev.locationtracker.model.firebase.firestore.RemoteDataSource
import com.gmail.assetkikbayev.locationtracker.utils.Constants
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
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
) {

    fun saveLocation(): Completable = locationProvider.observeLocation()
        .flatMapCompletable { location ->
            remoteServer.sendLocation(location)
                .onErrorResumeNext {
                    if (it.message == Constants.SERVER_ERROR) {
                        val coordinates = Location(
                            0,
                            location.longitude,
                            location.altitude,
                            SimpleDateFormat.getDateTimeInstance().format(Date()),
                            firebaseAuth.getCurrentUserId()!!
                        )
                        val action = localDB.save(coordinates)
                        //WorkManager schedule
                        return@onErrorResumeNext action
                    } else {
                        return@onErrorResumeNext Completable.error(it)
                    }
                }
        }

    fun stopLocationUpdates(): Completable = locationProvider.stopLocationProvider()
        .subscribeOn(Schedulers.io())

}
