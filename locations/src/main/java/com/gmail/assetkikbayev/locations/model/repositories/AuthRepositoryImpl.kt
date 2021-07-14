package com.gmail.assetkikbayev.locations.model.repositories

import com.gmail.assetkikbayev.locations.model.authentification.RemoteAuthSource
import io.reactivex.Completable
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: RemoteAuthSource,
) : AuthRepository {
    override fun loginByEmail(email: String, password: String): Completable =
        firebaseAuth.loginByEmail(email, password)

    override fun getCurrentUserId(): String? = firebaseAuth.getCurrentUserId()
}