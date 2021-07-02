package com.gmail.assetkikbayev.locationtracker.model.repositories

import com.gmail.assetkikbayev.locationtracker.model.firebase.authentification.RemoteAuthSource
import com.gmail.assetkikbayev.locationtracker.model.locationprovider.LocationStorage
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val locationStorage: LocationStorage,
    private val firebaseAuth: RemoteAuthSource
) : UserRepository {

    override fun saveLocation(): Completable = locationStorage.saveLocation()

    override fun logout(): Completable = firebaseAuth.logout()

    override fun stopLocationProvider(): Completable = locationStorage.stopLocationUpdates()

    override fun transferDataToRemoteFromDB(): Completable = locationStorage.transferToRemoteServer()

}