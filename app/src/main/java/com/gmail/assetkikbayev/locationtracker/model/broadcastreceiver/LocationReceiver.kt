package com.gmail.assetkikbayev.locationtracker.model.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.gmail.assetkikbayev.locationtracker.model.firebase.authentification.RemoteAuthSource
import com.gmail.assetkikbayev.locationtracker.model.services.LocationService
import dagger.android.AndroidInjection
import javax.inject.Inject

class LocationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var firebaseAuth: RemoteAuthSource

    override fun onReceive(context: Context, intent: Intent) {
        AndroidInjection.inject(this, context)
        if (!firebaseAuth.getCurrentUserId().isNullOrEmpty()) {
            if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
                val locationIntent = Intent(context, LocationService::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(locationIntent)
                } else {
                    context.startService(locationIntent)
                }
            }
        }
    }
}