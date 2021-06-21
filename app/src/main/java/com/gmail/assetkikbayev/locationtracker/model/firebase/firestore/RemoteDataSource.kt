package com.gmail.assetkikbayev.locationtracker.model.firebase.firestore

import android.location.Location
import com.gmail.assetkikbayev.locationtracker.model.firebase.authentification.RemoteAuthSource
import com.gmail.assetkikbayev.locationtracker.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single
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

    fun saveLocation(location: Location): Single<Long> = Single.create { emitter ->
        coordinates["USER_ID"] =
            firebaseAuth.getCurrentUser()!!
        coordinates["LONGITUDE"] = location.longitude
        coordinates["LATITUDE"] = location.latitude
        coordinates["TIMESTAMP"] = SimpleDateFormat.getDateTimeInstance().format(Date())
        if (firebaseAuth.getCurrentUser().isNullOrEmpty())
            firestore.collection("location")
                .document("${location.time}")
                .set(coordinates)
                .addOnSuccessListener {
                    emitter.onSuccess(null)
                    println("Firestore saved location")
                }
                .addOnFailureListener { emitter.onError(Throwable("SERVER_ERROR")) }
    }
}