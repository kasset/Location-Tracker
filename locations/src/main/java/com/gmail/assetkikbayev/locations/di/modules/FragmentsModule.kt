package com.gmail.assetkikbayev.locations.di.modules

import com.gmail.assetkikbayev.locations.ui.LoginFragment
import com.gmail.assetkikbayev.locations.ui.MapsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentsModule {
    @ContributesAndroidInjector
    fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    fun contributeMapsFragment(): MapsFragment
}