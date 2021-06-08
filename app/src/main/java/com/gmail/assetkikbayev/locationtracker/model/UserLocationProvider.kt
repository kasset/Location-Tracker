package com.gmail.assetkikbayev.locationtracker.model

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.gmail.assetkikbayev.locationtracker.model.firebase.authentification.FirebaseAuthSource
import com.gmail.assetkikbayev.locationtracker.model.firebase.firestore.FirebaseDataSource
import javax.inject.Inject

class UserLocationProvider @Inject constructor(
    private val firebaseAuth: FirebaseAuthSource,
    private val context: Context
) {
    private var hasGPS: Boolean = false
    private var hasNetwork: Boolean = false
    private var userCoordinates = mutableMapOf<String, Any>()

    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    fun getLocation(
        firestoreDB: FirebaseDataSource,
    ) {
        hasGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGPS || hasNetwork) {
            if (hasGPS) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                }
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    10.0F
                ) { location ->
                    userCoordinates["USER_ID"] = firebaseAuth.getCurrentUser()?.uid.toString()
                    userCoordinates["LONGITUDE"] = location.longitude
                    userCoordinates["LATITUDE"] = location.latitude
                    userCoordinates["TIMESTAMP"] = location.time
                    if (firebaseAuth.getCurrentUser() != null) {
                        firestoreDB.firestore.document("location/userLocations")
                            .set(location)
                            .addOnSuccessListener {
                                println("Successfully saved in Firestore Cloud")
                            }
                            .addOnFailureListener {  }
                    }
                }
            }
        }
    }

}