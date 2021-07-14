package com.gmail.assetkikbayev.locations.ui

import android.os.Bundle
import com.gmail.assetkikbayev.locations.R
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        androidInjector()
    }
}