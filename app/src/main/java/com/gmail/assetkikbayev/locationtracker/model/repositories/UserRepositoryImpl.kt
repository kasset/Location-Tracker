package com.gmail.assetkikbayev.locationtracker.model.repositories

import com.gmail.assetkikbayev.locationtracker.model.UserLocationProvider
import com.gmail.assetkikbayev.locationtracker.model.firebase.firestore.FirebaseDataSource
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firebaseDB: FirebaseDataSource,
    private val userLocation: UserLocationProvider
) : UserRepository {

    override fun saveLocation() {

    }

    override fun logout() {
       firebaseDB.logout()
    }
}