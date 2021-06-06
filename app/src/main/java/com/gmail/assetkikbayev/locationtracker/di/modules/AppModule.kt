package com.gmail.assetkikbayev.locationtracker.di.modules

import com.gmail.assetkikbayev.locationtracker.model.firebase.AuthRepository
import com.gmail.assetkikbayev.locationtracker.model.firebase.AuthRepositoryImpl
import com.gmail.assetkikbayev.locationtracker.model.firebase.UserRepository
import com.gmail.assetkikbayev.locationtracker.model.firebase.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Module(
    includes = [
        ViewModelsModule::class,
        FragmentsModule::class,
        ActivityModule::class
    ]
)
interface AppModule {
    @Binds
    @Singleton
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}