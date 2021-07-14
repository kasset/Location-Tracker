package com.gmail.assetkikbayev.locations.di.modules

import com.gmail.assetkikbayev.locations.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {
    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity
}