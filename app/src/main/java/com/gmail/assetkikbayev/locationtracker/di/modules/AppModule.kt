package com.gmail.assetkikbayev.locationtracker.di.modules

import com.gmail.assetkikbayev.locationtracker.model.firebase.AuthRepository
import com.gmail.assetkikbayev.locationtracker.model.firebase.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Module(
    includes = [
        AndroidSupportInjectionModule::class,
        ViewModelsModule::class,
        FragmentsModule::class,
        ActivityModule::class
    ]
)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindUserRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}