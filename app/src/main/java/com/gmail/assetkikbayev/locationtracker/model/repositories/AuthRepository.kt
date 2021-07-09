package com.gmail.assetkikbayev.locationtracker.model.repositories

import io.reactivex.Completable

interface AuthRepository {
    fun loginByEmail(email: String, password: String): Completable
    fun registerByEmail(email: String, password: String): Completable
    fun getCurrentUserId(): String?
}