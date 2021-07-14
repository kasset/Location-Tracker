package com.gmail.assetkikbayev.locations.di.modules

import com.gmail.assetkikbayev.locations.model.repositories.AuthRepository
import com.gmail.assetkikbayev.locations.model.repositories.AuthRepositoryImpl
import com.gmail.assetkikbayev.locations.model.repositories.MapsRepository
import com.gmail.assetkikbayev.locations.model.repositories.MapsRepositoryImpl
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
    fun bindUserRepository(userRepositoryImpl: MapsRepositoryImpl): MapsRepository
}