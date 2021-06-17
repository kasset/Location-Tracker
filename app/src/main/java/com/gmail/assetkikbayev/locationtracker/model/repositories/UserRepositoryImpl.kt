package com.gmail.assetkikbayev.locationtracker.model.repositories

import com.gmail.assetkikbayev.locationtracker.model.LocationStorage
import com.gmail.assetkikbayev.locationtracker.model.firebase.authentification.FirebaseAuthSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val locationStorage: LocationStorage,
    private val firebaseAuth: FirebaseAuthSource
) : UserRepository {

    override fun saveLocation(): Disposable = locationStorage.saveLocation()

    override fun logout(): Completable = firebaseAuth.logout()

}