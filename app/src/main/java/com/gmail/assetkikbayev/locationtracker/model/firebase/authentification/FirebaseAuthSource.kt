package com.gmail.assetkikbayev.locationtracker.model.firebase.authentification

import com.gmail.assetkikbayev.locationtracker.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    fun registerByEmail(
        email: String,
        password: String
    ): Completable = Completable.create { emitter ->
        firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener { task ->
            if (task.result?.signInMethods?.size == 0) {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (!emitter.isDisposed) {
                        if (it.isSuccessful)
                            emitter.onComplete()
                        else
                            emitter.onError(it.exception)
                    }
                }
            } else {
                emitter.onError(Throwable(Constants.USER_EXIST))
            }
        }
    }.subscribeOn(Schedulers.io())


    fun loginByEmail(
        email: String,
        password: String
    ): Completable = Completable.create { emitter ->
//        if (firebaseAuth.currentUser == null) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception)
            }
        }
//        } else {
//            emitter.onError(Throwable("USER_ALREADY_SIGNED"))
//        }
    }.subscribeOn(Schedulers.io())

    fun getCurrentUser() = firebaseAuth.currentUser?.uid

    fun logout(): Completable = Completable.fromAction {
        firebaseAuth.signOut()
    }.subscribeOn(Schedulers.io())
}