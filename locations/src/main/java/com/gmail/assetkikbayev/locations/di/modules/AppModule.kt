package com.gmail.assetkikbayev.locations.di.modules

import android.app.Application
import android.content.Context
import com.gmail.assetkikbayev.locations.di.AppB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        ActivityModule::class,
        FragmentsModule::class,
        FirebaseModule::class,
        ViewModelsModule::class,
        RepositoriesModule::class,
    ]
)
class AppModule {
    @Provides
    @Singleton
    fun provideApp(application: Application): AppB = application as AppB

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app.applicationContext
}