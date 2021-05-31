package com.gmail.assetkikbayev.locationtracker.model.firebase

import com.google.firebase.auth.FirebaseUser
import io.reactivex.rxjava3.core.Completable

interface AuthRepository {
    fun loginByEmail(email: String, password: String): Completable
    fun registerByEmail(email: String, password: String): Completable
    fun logout()
    fun getCurrentUser(): FirebaseUser?
}