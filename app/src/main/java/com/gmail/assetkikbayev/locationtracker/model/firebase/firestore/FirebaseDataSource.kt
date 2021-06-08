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

    val firestore: FirebaseFirestore
        get() = db

}