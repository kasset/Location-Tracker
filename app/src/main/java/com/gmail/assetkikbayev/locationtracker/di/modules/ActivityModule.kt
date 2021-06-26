package com.gmail.assetkikbayev.locationtracker.di.modules

import com.gmail.assetkikbayev.locationtracker.model.services.LocationService
import com.gmail.assetkikbayev.locationtracker.ui.AppActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {
    @ContributesAndroidInjector
    fun contributeAppActivity(): AppActivity

    @ContributesAndroidInjector
    fun contributeLocationService(): LocationService
}