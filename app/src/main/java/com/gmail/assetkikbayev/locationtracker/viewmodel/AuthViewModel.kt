package com.gmail.assetkikbayev.locationtracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.assetkikbayev.locationtracker.model.repositories.AuthRepositoryImpl
import com.gmail.assetkikbayev.locationtracker.utils.Resource
import com.gmail.assetkikbayev.locationtracker.utils.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class AuthViewModel
@Inject constructor(
    private val authRepository: AuthRepositoryImpl
) : BaseViewModel() {

    private val authLiveData = MutableLiveData<Resource<Nothing>>()

    val getAuthLiveData: LiveData<Resource<Nothing>>
        get() = authLiveData

    fun loginByEmail(email: String, password: String) {
        authRepository.loginByEmail(email, password)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { authLiveData.value = Resource.Loading() }
            .subscribe(
                { authLiveData.value = Resource.Success() },
                { authLiveData.value = Resource.Failure(it) })
            .addTo(disposableBag)

    }

    fun registerByEmail(email: String, password: String) {
        authRepository.registerByEmail(email, password)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { authLiveData.value = Resource.Loading() }
            .subscribe({ authLiveData.value = Resource.Success() },
                { authLiveData.value = Resource.Failure(it) })
            .addTo(disposableBag)

    }

}