package com.gmail.assetkikbayev.locations.model.data

import com.gmail.assetkikbayev.locations.model.authentification.RemoteAuthSource
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: RemoteAuthSource,
) {
    fun getLocation(): Observable<DocumentSnapshot> =
        Observable.create { emitter ->
            firestore.collection("locations")
                .document(firebaseAuth.getCurrentUserId()!!)
                .collection("coordinates")
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        emitter.onError(Throwable(error.message))
                        return@addSnapshotListener
                    }
                    value?.let {
                        value.documents.forEach { location ->
                            emitter.onNext(location)
                        }
                    }
                }
        }
}