package com.gmail.assetkikbayev.locationtracker.model.firebase

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuthSource,
    private val context: Context
) {
    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun logout() {
        firebaseAuth.logout()
    }

    fun saveLocation() {

        val locationRequest = LocationRequest.create().apply {
            interval = 5000

        }
          var fusedLocationClient =
              LocationServices.getFusedLocationProviderClient(context)
          if (ActivityCompat.checkSelfPermission(
                  context,
                  Manifest.permission.ACCESS_FINE_LOCATION
              ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                  context,
                  Manifest.permission.ACCESS_COARSE_LOCATION
              ) != PackageManager.PERMISSION_GRANTED
          ) {

              return
          }
//        fusedLocationClient.requestLocationUpdates(locationRequest, object: LocationCallback())

        val coordinates = mutableMapOf<String, Any>(

        )
        if (firebaseAuth.getCurrentUser() != null) {
            db.document("location/userCoordinates")
                .set(coordinates)
                .addOnSuccessListener { }
                .addOnFailureListener {}
        }
    }

}