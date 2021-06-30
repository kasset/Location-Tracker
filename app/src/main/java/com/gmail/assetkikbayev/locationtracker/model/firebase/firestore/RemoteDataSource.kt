package com.gmail.assetkikbayev.locationtracker.model.firebase.firestore


import com.gmail.assetkikbayev.locationtracker.model.db.Location
import com.gmail.assetkikbayev.locationtracker.model.firebase.authentification.RemoteAuthSource
import com.gmail.assetkikbayev.locationtracker.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.rxjava3.core.Completable
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

    fun sendLocation(location: Location): Completable = Completable.create { emitter ->
        coordinates["USER_ID"] =
            firebaseAuth.getCurrentUserId()!!
        coordinates["LONGITUDE"] = location.longitude
        coordinates["LATITUDE"] = location.latitude
        coordinates["TIMESTAMP"] = SimpleDateFormat.getDateTimeInstance().format(Date())
        if (firebaseAuth.getCurrentUserId().isNullOrEmpty())
            firestore.collection("locations")
                .document(firebaseAuth.getCurrentUserId()!!)
                .collection("coordinates")
                .document("${location.timestamp}")
                .set(coordinates)
                .addOnSuccessListener { emitter.onComplete() }
                .addOnFailureListener { emitter.onError(Throwable(Constants.SERVER_ERROR)) }
    }
}