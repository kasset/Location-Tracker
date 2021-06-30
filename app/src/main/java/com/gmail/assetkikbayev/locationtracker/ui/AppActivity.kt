package com.gmail.assetkikbayev.locationtracker.ui

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.gmail.assetkikbayev.locationtracker.R
import dagger.android.support.DaggerAppCompatActivity

class AppActivity : DaggerAppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        androidInjector()
    }
}