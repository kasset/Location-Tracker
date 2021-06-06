package com.gmail.assetkikbayev.locationtracker.model.firebase

import com.google.firebase.auth.FirebaseAuth
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthSource @Inject constructor() {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun checkEmailExistOrNot(email: String): Completable = Completable.create { emitter ->
        firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener { task ->
            if (task.result?.signInMethods?.size == 0) {
                if (task.isSuccessful) {
                    emitter.onComplete()
                }
            } else {
                emitter.onError(task.exception)
            }
        }
    }.subscribeOn(Schedulers.io())

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

    }.subscribeOn(Schedulers.io())


    fun loginByEmail(
        email: String,
        password: String
    ): Completable = Completable.create { emitter ->
        if (firebaseAuth.currentUser == null) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (!emitter.isDisposed) {
                    if (it.isSuccessful)
                        emitter.onComplete()
                    else
                        emitter.onError(it.exception)
                }
            }
        }
    }.subscribeOn(Schedulers.io())

    fun logout() = firebaseAuth.signOut()

    fun getCurrentUser() = firebaseAuth.currentUser
}