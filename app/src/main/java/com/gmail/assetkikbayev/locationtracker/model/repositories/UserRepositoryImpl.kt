package com.gmail.assetkikbayev.locationtracker.model.repositories

import com.gmail.assetkikbayev.locationtracker.model.UserLocationProvider
import com.gmail.assetkikbayev.locationtracker.model.db.Location
import com.gmail.assetkikbayev.locationtracker.model.db.LocationDao
import com.gmail.assetkikbayev.locationtracker.model.firebase.authentification.FirebaseAuthSource
import com.gmail.assetkikbayev.locationtracker.model.firebase.firestore.FirebaseDataSource
import io.reactivex.rxjava3.disposables.Disposable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val cloudStore: FirebaseDataSource,
    private val firebaseAuth: FirebaseAuthSource,
    private val locationProvider: UserLocationProvider,
    private val localDB: LocationDao
) : UserRepository {

    private var coordinates = mutableMapOf<String, Any>()

    override fun saveLocation(): Disposable = locationProvider.observeLocation()
        .subscribe(
            { location ->
                coordinates["USER_ID"] =
                    firebaseAuth.getCurrentUser()?.uid.toString()
                coordinates["LONGITUDE"] = location.longitude
                coordinates["LATITUDE"] = location.latitude
                coordinates["TIMESTAMP"] = SimpleDateFormat.getDateTimeInstance().format(Date())
                if (firebaseAuth.getCurrentUser() != null) {
                    cloudStore.firestore.collection("location")
                        .document("${location.time}")
                        .set(coordinates)
                        .addOnSuccessListener {
                            println("Successfully saved in Firestore Cloud")
                        }
                        .addOnFailureListener {
                            val coordinates = Location(
                                0,
                                location.longitude,
                                location.altitude,
                                SimpleDateFormat.getDateTimeInstance().format(Date()),
                                firebaseAuth.getCurrentUser()?.uid.toString()
                            )
                            localDB.save(coordinates)
                        }
                }
            },
            {}
        )

    override fun logout() {
        firebaseAuth.logout()
    }

}