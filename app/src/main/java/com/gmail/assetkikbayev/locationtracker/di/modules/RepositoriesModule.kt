package com.gmail.assetkikbayev.locationtracker.di.modules

import com.gmail.assetkikbayev.locationtracker.model.repositories.AuthRepository
import com.gmail.assetkikbayev.locationtracker.model.repositories.AuthRepositoryImpl
import com.gmail.assetkikbayev.locationtracker.model.repositories.UserRepository
import com.gmail.assetkikbayev.locationtracker.model.repositories.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoriesModule {
    @Binds
    @Singleton
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}