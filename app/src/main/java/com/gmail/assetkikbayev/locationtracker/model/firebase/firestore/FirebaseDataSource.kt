package com.gmail.assetkikbayev.locationtracker.model.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    val firestoreCloud: FirebaseFirestore
        get() = firestore
}