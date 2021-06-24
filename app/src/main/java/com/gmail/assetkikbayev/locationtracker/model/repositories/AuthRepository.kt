package com.gmail.assetkikbayev.locationtracker.model.repositories

import io.reactivex.rxjava3.core.Completable

interface AuthRepository {
    fun loginByEmail(email: String, password: String): Completable
    fun registerByEmail(email: String, password: String): Completable
    fun getCurrentUserId(): String?
}