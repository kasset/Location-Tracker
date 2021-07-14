package com.gmail.assetkikbayev.locations.di


import com.gmail.assetkikbayev.locations.di.components.DaggerAppBComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class AppB : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppBComponent.builder().app(this).build()
    }

}