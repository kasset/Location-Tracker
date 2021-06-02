package com.gmail.assetkikbayev.locationtracker.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.assetkikbayev.locationtracker.model.firebase.AuthRepositoryImpl
import com.gmail.assetkikbayev.locationtracker.utils.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AuthViewModel
@Inject constructor(
    private val authRepository: AuthRepositoryImpl
) : BaseViewModel(), LifecycleObserver {

    private val authLiveData = MutableLiveData<Resource<Nothing>>()

    fun loginByEmail(email: String, password: String) {
        addDisposable(authRepository.loginByEmail(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { authLiveData.value = Resource.Loading() }
            .subscribe(
                { authLiveData.value = Resource.Success() },
                { authLiveData.value = Resource.Failure(it) })
        )
    }

    fun registerByEmail(email: String, password: String) {
        addDisposable(authRepository.registerByEmail(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { authLiveData.value = Resource.Loading() }
            .subscribe({ authLiveData.value = Resource.Success() },
                { authLiveData.value = Resource.Failure(it) })
        )
    }

    fun logout() {
        authRepository.logout()
    }

    fun getAuthData(): LiveData<Resource<Nothing>> {
        return authLiveData
    }

}