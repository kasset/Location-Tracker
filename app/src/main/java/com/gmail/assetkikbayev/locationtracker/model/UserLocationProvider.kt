package com.gmail.assetkikbayev.locationtracker.model

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager

import android.os.Bundle
import androidx.core.app.ActivityCompat
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class UserLocationProvider @Inject constructor(
    private val context: Context
) {
    private var hasGPS: Boolean = false
    private var hasNetwork: Boolean = false
    private val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    fun observeLocation(): Observable<Location> = Observable.create { emitter ->
        hasGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGPS) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                emitter.onError(Throwable("GPS_PERMISSION"))
            }
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                10.0F,
                object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        emitter.onNext(location)
                    }
                }
            )
        } else {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                emitter.onError(Throwable("NETWORK_PERMISSION"))
            }
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                5000,
                10.0F,
                object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        emitter.onNext(location)
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                        super.onStatusChanged(provider, status, extras)
                    }
                })
        }
    }
}
