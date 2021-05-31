package com.gmail.assetkikbayev.locationtracker.model.firebase

import com.google.firebase.auth.FirebaseAuth
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthSource @Inject constructor() {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun registerByEmail(
        email: String,
        password: String
    ): Completable = Completable.create { emitter ->
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception)
            }
        }
    }

    fun loginByEmail(
        email: String,
        password: String
    ): Completable = Completable.create { emitter ->
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception)
            }
        }
    }

    fun logout() = firebaseAuth.signOut()

    fun getCurrentUser() = firebaseAuth.currentUser
}