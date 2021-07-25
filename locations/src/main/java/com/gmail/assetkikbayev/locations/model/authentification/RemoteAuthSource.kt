package com.gmail.assetkikbayev.locations.model.authentification

import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteAuthSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    fun loginByEmail(
        email: String,
        password: String
    ): Completable = Completable.create { emitter ->
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(Throwable(it.exception?.message))
            }
        }
    }.subscribeOn(Schedulers.io())

    fun getCurrentUserId() = firebaseAuth.currentUser?.uid

    fun logout(): Completable = Completable.fromAction {
        firebaseAuth.signOut()
    }.subscribeOn(Schedulers.io())
}