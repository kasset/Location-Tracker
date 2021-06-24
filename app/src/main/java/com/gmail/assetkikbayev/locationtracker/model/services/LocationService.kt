package com.gmail.assetkikbayev.locationtracker.model.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.gmail.assetkikbayev.locationtracker.model.locationprovider.UserLocationProvider
import com.gmail.assetkikbayev.locationtracker.model.repositories.UserRepository
import com.gmail.assetkikbayev.locationtracker.model.repositories.UserRepositoryImpl
import javax.inject.Inject

class LocationService @Inject constructor(
    private val userRepo: UserRepositoryImpl
) : Service() {

    override fun onBind(intent: Intent): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= 26) {
            val channelId = "location_channel"
            val channel = NotificationChannel(
                channelId,
                "My channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            val notification = NotificationCompat.Builder(this, channelId)
                .setContentTitle("Location Tracker")
                .setContentText("")
                .build()
            startForeground(1, notification)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        userRepo.saveLocation()
        return START_NOT_STICKY
    }

}