package com.gmail.assetkikbayev.locationtracker.model.firebase

import com.google.firebase.auth.FirebaseUser
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuthSource,
) : AuthRepository {
    override fun loginByEmail(email: String, password: String): Completable =
        firebaseAuth.loginByEmail(email, password)

    override fun registerByEmail(email: String, password: String): Completable =
        firebaseAuth.registerByEmail(email, password)

    override fun checkEmailExistOrNot(email: String): Completable =
        firebaseAuth.checkEmailExistOrNot(email)

    override fun getCurrentUser(): FirebaseUser? = firebaseAuth.getCurrentUser()
}