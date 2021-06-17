package com.gmail.assetkikbayev.locationtracker.model

import com.gmail.assetkikbayev.locationtracker.model.db.Location
import com.gmail.assetkikbayev.locationtracker.model.db.LocationDao
import com.gmail.assetkikbayev.locationtracker.model.firebase.authentification.FirebaseAuthSource
import com.gmail.assetkikbayev.locationtracker.model.firebase.firestore.FirebaseDataSource
import com.gmail.assetkikbayev.locationtracker.utils.Constants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationStorage @Inject constructor(
    private val remoteServer: FirebaseDataSource,
    private val firebaseAuth: FirebaseAuthSource,
    private val localDB: LocationDao,
    private val locationProvider: UserLocationProvider,
) {
    private var coordinates = mutableMapOf<String, Any>()

    fun saveLocation(): Disposable = locationProvider.observeLocation()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { location ->
                coordinates["USER_ID"] =
                    firebaseAuth.getCurrentUser()
                coordinates["LONGITUDE"] = location.longitude
                coordinates["LATITUDE"] = location.latitude
                coordinates["TIMESTAMP"] = SimpleDateFormat.getDateTimeInstance().format(Date())
                if (firebaseAuth.getCurrentUser().isNotEmpty()) {
                    remoteServer.firestoreCloud.collection("location")
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
                                firebaseAuth.getCurrentUser()
                            )
                            val disposable = localDB.save(coordinates)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe()
                            println("Successfully saved in Local DB")
                        }
                }
            },
            {
                if (it?.message == Constants.LOCATION_PERMISSION_ERROR) {

                }
            }
        )
}
