package com.gmail.assetkikbayev.locationtracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.assetkikbayev.locationtracker.model.firebase.AuthRepositoryImpl
import com.gmail.assetkikbayev.locationtracker.utils.Resource
import com.google.firebase.auth.FirebaseUser
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class AuthViewModel
@Inject constructor(
    private val authRepository: AuthRepositoryImpl
) : BaseViewModel() {

    private val authLiveData = MutableLiveData<Resource<Nothing>>()
    private val emailLiveData = MutableLiveData<Resource<Nothing>>()

    val getAuthLiveData: LiveData<Resource<Nothing>>
        get() = authLiveData

    val getEmailLiveData: LiveData<Resource<Nothing>>
        get() = emailLiveData


    fun checkEmailExistOrNot(email: String) {
        disposableBag.add(authRepository.checkEmailExistOrNot(email)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { emailLiveData.value = Resource.Loading() }
            .subscribe(
                { emailLiveData.value = Resource.Success() },
                { emailLiveData.value = Resource.Failure() }
            )
        )
    }

    fun loginByEmail(email: String, password: String) {
        disposableBag.add(authRepository.loginByEmail(email, password)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { authLiveData.value = Resource.Loading() }
            .subscribe(
                { authLiveData.value = Resource.Success() },
                { authLiveData.value = Resource.Failure(it) })
        )
    }

    fun registerByEmail(email: String, password: String) {
        disposableBag.add(authRepository.registerByEmail(email, password)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { authLiveData.value = Resource.Loading() }
            .subscribe({ authLiveData.value = Resource.Success() },
                { authLiveData.value = Resource.Failure(it) })
        )
    }

    fun getCurrentUser(): FirebaseUser? {
        return authRepository.getCurrentUser()
    }
}