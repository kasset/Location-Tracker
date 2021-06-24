package com.gmail.assetkikbayev.locationtracker.model.firebase.firestore

import android.location.Location
import com.gmail.assetkikbayev.locationtracker.model.firebase.authentification.RemoteAuthSource
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: RemoteAuthSource,
) {
    private var coordinates = mutableMapOf<String, Any>()

    fun saveLocation(location: Location): Completable = Completable.create { emitter ->
        coordinates["USER_ID"] =
            firebaseAuth.getCurrentUserId()!!
        coordinates["LONGITUDE"] = location.longitude
        coordinates["LATITUDE"] = location.latitude
        coordinates["TIMESTAMP"] = SimpleDateFormat.getDateTimeInstance().format(Date())
        if (firebaseAuth.getCurrentUserId().isNullOrEmpty())
            firestore.collection("locations")
                .document(firebaseAuth.getCurrentUserId()!!)
                .collection("coordinates")
                .document("${location.time}")
                .set(coordinates)
                .addOnSuccessListener { emitter.onComplete() }
                .addOnFailureListener { emitter.onError(Throwable("SERVER_ERROR")) }
    }.subscribeOn(Schedulers.io())
}