package com.gmail.assetkikbayev.locationtracker.model.repositories

import io.reactivex.Completable


interface UserRepository {
    fun saveLocation(): Completable
    fun logout(): Completable
    fun stopLocationProvider(): Completable
    fun transferDataToRemoteFromDB(): Completable
}