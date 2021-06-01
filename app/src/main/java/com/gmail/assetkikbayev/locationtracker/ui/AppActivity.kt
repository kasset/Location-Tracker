package com.gmail.assetkikbayev.locationtracker.ui

import android.os.Bundle
import androidx.navigation.findNavController
import com.gmail.assetkikbayev.locationtracker.R
import dagger.android.support.DaggerAppCompatActivity

class AppActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        androidInjector()
        val loginFragment = LoginFragment()
        val fm = supportFragmentManager
        fm.beginTransaction().add(R.id.main_container, loginFragment)
            .addToBackStack(null)
            .commit()
    }
}