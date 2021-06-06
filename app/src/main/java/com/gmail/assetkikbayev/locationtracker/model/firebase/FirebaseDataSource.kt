package com.gmail.assetkikbayev.locationtracker.model.firebase

import com.google.android.gms.location.LocationRequest
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuthSource
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
        /*  var fusedLocationClient =
              LocationServices.getFusedLocationProviderClient(this)
          if (ActivityCompat.checkSelfPermission(
                  this,
                  Manifest.permission.ACCESS_FINE_LOCATION
              ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                  this,
                  Manifest.permission.ACCESS_COARSE_LOCATION
              ) != PackageManager.PERMISSION_GRANTED
          ) {
              // TODO: Consider calling
              //    ActivityCompat#requestPermissions
              // here to request the missing permissions, and then overriding
              //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
              //                                          int[] grantResults)
              // to handle the case where the user grants the permission. See the documentation
              // for ActivityCompat#requestPermissions for more details.
              return
          }*/
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