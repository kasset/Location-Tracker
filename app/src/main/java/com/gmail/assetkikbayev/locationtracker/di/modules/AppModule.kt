package com.gmail.assetkikbayev.locationtracker.di.modules

import android.content.Context
import com.gmail.assetkikbayev.locationtracker.di.App
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(
    includes = [
        ViewModelsModule::class,
        FragmentsModule::class,
        ActivityModule::class,
        RepositoriesModule::class
    ]
)
interface AppModule {
    @Binds
    @Singleton
    fun bindContext(application: App): Context
}