package com.gmail.assetkikbayev.locationtracker.model.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.gmail.assetkikbayev.locationtracker.model.repositories.UserRepositoryImpl
import com.gmail.assetkikbayev.locationtracker.utils.Constants
import dagger.android.AndroidInjection
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class LocationService : Service() {

    @Inject
    lateinit var userRepo: UserRepositoryImpl
    private var isStarted: Boolean = false

    override fun onBind(intent: Intent): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
        getNotification()
        isStarted = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (isStarted) {
            userRepo.saveLocation()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
            isStarted = false
        }
        return START_NOT_STICKY
    }

    private fun getNotification() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = getChannelId(Constants.LOCATION_CHANNEL_ID)
            val channel = NotificationChannel(
                channelId,
                Constants.LOCATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
            val notification = NotificationCompat.Builder(this, channelId)
                .setContentTitle(Constants.LOCATION_NOTIFICATION_TITLE)
                .setContentText(Constants.LOCATION_NOTIFICATION_TEXT)
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
                .build()
            startForeground(1, notification)
        } else {
            val notification = NotificationCompat.Builder(this)
                .setContentTitle(Constants.LOCATION_NOTIFICATION_TITLE)
                .setContentText(Constants.LOCATION_NOTIFICATION_TEXT)
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
                .build()
            startForeground(2, notification)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getChannelId(channelId: String): String {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notificationChannels.forEach { channel ->
            if (channel.id.equals(channelId)) {
                return channel.id
            }
        }
        return channelId
    }
}