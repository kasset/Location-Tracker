package com.gmail.assetkikbayev.locationtracker.model

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.gmail.assetkikbayev.locationtracker.utils.Constants
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocationProvider @Inject constructor(
    private val context: Context
) {
    private var hasGPS = false
    private var hasNetwork = false

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
                emitter.onError(Throwable(Constants.LOCATION_PERMISSION_ERROR))
            }
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                Constants.TIME_INTERVAL_UPDATE,
                Constants.DISTANCE_INTERVAL_UPDATE,
                object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        emitter.onNext(location)
                        emitter.onComplete()
                    }

                    override fun onStatusChanged(
                        provider: String?,
                        status: Int,
                        extras: Bundle?
                    ) {
                        super.onStatusChanged(provider, status, extras)
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
                emitter.onError(Throwable(Constants.LOCATION_PERMISSION_ERROR))
            }
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                Constants.TIME_INTERVAL_UPDATE,
                Constants.DISTANCE_INTERVAL_UPDATE,
                object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        emitter.onNext(location)
                        emitter.onComplete()
                    }

                    override fun onStatusChanged(
                        provider: String?,
                        status: Int,
                        extras: Bundle?
                    ) {
                        super.onStatusChanged(provider, status, extras)
                    }
                }
            )
        }
    }
}
