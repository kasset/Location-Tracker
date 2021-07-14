package com.gmail.assetkikbayev.locations.model.repositories

import io.reactivex.Completable

interface AuthRepository {
    fun loginByEmail(email: String, password: String): Completable
    fun getCurrentUserId(): String?
}