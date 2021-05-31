package com.gmail.assetkikbayev.locationtracker.di.modules

import com.gmail.assetkikbayev.locationtracker.ui.LoginFragment
import com.gmail.assetkikbayev.locationtracker.ui.SignupFragment
import com.gmail.assetkikbayev.locationtracker.ui.UserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentsModule {

    @ContributesAndroidInjector
    fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    fun contributeSignupFragment(): SignupFragment

    @ContributesAndroidInjector
    fun contributeUserFragment(): UserFragment
}