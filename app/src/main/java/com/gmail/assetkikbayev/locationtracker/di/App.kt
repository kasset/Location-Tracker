package com.gmail.assetkikbayev.locationtracker.di

import com.gmail.assetkikbayev.locationtracker.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().app(this).build()
    }
}