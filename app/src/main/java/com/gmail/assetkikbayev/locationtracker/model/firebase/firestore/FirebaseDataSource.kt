package com.gmail.assetkikbayev.locationtracker.model.firebase.firestore

import com.gmail.assetkikbayev.locationtracker.model.firebase.authentification.FirebaseAuthSource
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuthSource
) {
    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun logout() {
        firebaseAuth.logout()
    }

    fun saveLocation(location: Map<String, Any>): Completable = Completable.create {
        if (firebaseAuth.getCurrentUser() != null) {
            db.document("location/userLocations")
                .set(location)
                .addOnSuccessListener { }
                .addOnFailureListener { }
        }
    }.subscribeOn(Schedulers.io())

}