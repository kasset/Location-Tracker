package com.gmail.assetkikbayev.locationtracker.model.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.gmail.assetkikbayev.locationtracker.model.repositories.UserRepositoryImpl
import com.gmail.assetkikbayev.locationtracker.utils.Constants
import dagger.android.AndroidInjection
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class LocationService : Service() {

    @Inject
    lateinit var userRepo: UserRepositoryImpl

    override fun onBind(intent: Intent): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
                .setContentTitle(Constants.LOCATION_NOTIFICATION_TITLE)
                .setContentText(Constants.LOCATION_NOTIFICATION_TEXT)
                .build()
            startForeground(1, notification)
        } else {
            var notification = NotificationCompat.Builder(this)
                .setContentTitle(Constants.LOCATION_NOTIFICATION_TITLE)
                .setContentText(Constants.LOCATION_NOTIFICATION_TEXT)
                .build()
            startForeground(2, notification)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        userRepo.saveLocation()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        return START_NOT_STICKY
    }
}