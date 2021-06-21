package com.gmail.assetkikbayev.locationtracker.model

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.gmail.assetkikbayev.locationtracker.utils.Constants
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocationProvider @Inject constructor(
    private val context: Context
) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private var locationCallback: LocationCallback = object : LocationCallback() {}
    private val request = LocationRequest.create().apply {
        interval = Constants.TIME_INTERVAL_UPDATE
        fastestInterval = Constants.TIME_FAST_INTERVAL_UPDATE
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    fun observeLocation(): Observable<Location> = Observable.create { emitter ->
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)
                result.locations.forEach { location ->
                    emitter.onNext(location)
                    println("-------coordinates" + location.time)
                }
            }
        }
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            emitter.onError(Throwable(Constants.LOCATION_PERMISSION_ERROR))
        }
        fusedLocationClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
    }


    fun stopLocationProvider(): Completable = Completable.create {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }.subscribeOn(Schedulers.io())

}
