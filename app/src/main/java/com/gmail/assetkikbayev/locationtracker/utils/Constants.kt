package com.gmail.assetkikbayev.locationtracker.utils

object Constants {
    //Location provider settings
    const val REQUEST_CODE_BACKGROUND = 1
    const val TIME_INTERVAL_UPDATE = 5000L
    const val TIME_FAST_INTERVAL_UPDATE = 10000L
    //custom errors
    const val LOCATION_PERMISSION_ERROR = "LOCATION_PERMISSION"
    const val USER_EXIST = "USER_EXIST"
    const val SERVER_ERROR = "SERVER_ERROR"
    //database
    const val DB_FILE_NAME = "LocationTracker_database"
    // services notifications
    const val LOCATION_NOTIFICATION_TITLE = "Location Tracker"
    const val LOCATION_NOTIFICATION_TEXT = "Service is launched"
}