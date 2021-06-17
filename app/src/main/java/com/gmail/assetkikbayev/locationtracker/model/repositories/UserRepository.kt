package com.gmail.assetkikbayev.locationtracker.model.repositories


import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.Disposable

interface UserRepository {
    fun saveLocation(): Disposable
    fun logout(): Completable
}