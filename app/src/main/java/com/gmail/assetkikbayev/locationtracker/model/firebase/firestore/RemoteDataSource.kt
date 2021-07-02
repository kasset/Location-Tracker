package com.gmail.assetkikbayev.locationtracker.model.firebase.firestore


import com.gmail.assetkikbayev.locationtracker.model.db.Location
import com.gmail.assetkikbayev.locationtracker.model.db.LocationDao
import com.gmail.assetkikbayev.locationtracker.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore

import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val localDB: LocationDao,
) {
    private var coordinates = mutableMapOf<String, Any>()

    fun sendLocation(location: Location): Completable = Completable.create { emitter ->
        coordinates["USER_ID"] = location.userId
        coordinates["LONGITUDE"] = location.longitude
        coordinates["LATITUDE"] = location.latitude
        coordinates["TIMESTAMP"] = location.timestamp
        if (location.userId.isNotEmpty()) {
            firestore.collection("locations")
                .document(location.userId)
                .collection("coordinates")
                .document(location.timestamp)
                .set(coordinates)
                .addOnSuccessListener { emitter.onComplete() }
                .addOnFailureListener { emitter.onError(Throwable(Constants.SERVER_ERROR)) }
        }
    }

    fun sendLocationsList(locations: List<Location>): Completable = Completable.create { emitter ->
        locations.forEach { location ->
            coordinates["USER_ID"] = location.userId
            coordinates["LONGITUDE"] = location.longitude
            coordinates["LATITUDE"] = location.latitude
            coordinates["TIMESTAMP"] = location.timestamp
            if (location.userId.isNotEmpty()) {
                firestore.collection("locations")
                    .document(location.userId)
                    .collection("coordinates")
                    .document(location.timestamp)
                    .set(coordinates)
                    .addOnSuccessListener {
                        localDB.delete(location)
                        emitter.onComplete()
                    }
                    .addOnFailureListener { emitter.onError(Throwable(Constants.SERVER_ERROR)) }
            }
        }
    }

}