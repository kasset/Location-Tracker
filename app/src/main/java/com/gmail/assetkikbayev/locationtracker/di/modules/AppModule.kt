package com.gmail.assetkikbayev.locationtracker.di.modules

import android.app.Application
import android.content.Context
import androidx.work.WorkManager
import com.gmail.assetkikbayev.locationtracker.di.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        ViewModelsModule::class,
        FragmentsModule::class,
        ActivityModule::class,
        RepositoriesModule::class,
        DatabaseModule::class,
        FirebaseModule::class,
    ]
)
class AppModule {
    @Provides
    @Singleton
    fun provideApp(application: Application): App = application as App

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app.applicationContext

    @Provides
    @Singleton
    fun providerWorkManager(context: Context): WorkManager = WorkManager.getInstance(context)
}