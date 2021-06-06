package com.gmail.assetkikbayev.locationtracker.model.firebase

import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firebaseDB: FirebaseDataSource
) : UserRepository {

    override fun saveLocation() {
        firebaseDB.saveLocation()
    }

    override fun logout() {
       firebaseDB.logout()
    }
}