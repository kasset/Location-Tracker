package com.gmail.assetkikbayev.locationtracker.model.repositories


import io.reactivex.rxjava3.core.Completable

interface UserRepository {
    fun saveLocation(): Completable
    fun logout(): Completable
    fun stopLocationProvider(): Completable
    fun transferDataToRemoteFromDB(): Completable
}