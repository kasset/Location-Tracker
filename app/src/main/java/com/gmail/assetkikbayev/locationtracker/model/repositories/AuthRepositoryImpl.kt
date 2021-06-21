package com.gmail.assetkikbayev.locationtracker.model.repositories

import com.gmail.assetkikbayev.locationtracker.model.firebase.authentification.RemoteAuthSource
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: RemoteAuthSource,
) : AuthRepository {
    override fun loginByEmail(email: String, password: String): Completable =
        firebaseAuth.loginByEmail(email, password)

    override fun registerByEmail(email: String, password: String): Completable =
        firebaseAuth.registerByEmail(email, password)

    override fun getCurrentUser(): String? = firebaseAuth.getCurrentUser()
}