package com.gmail.assetkikbayev.locationtracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.assetkikbayev.locationtracker.model.repositories.UserRepositoryImpl
import com.gmail.assetkikbayev.locationtracker.utils.Resource
import com.gmail.assetkikbayev.locationtracker.utils.addTo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl
) : BaseViewModel() {

    private val userLiveData = MutableLiveData<Resource<Nothing>>()

    val getUserLiveData: LiveData<Resource<Nothing>>
        get() = userLiveData

    fun saveLocation() {
        userRepository.saveLocation()
            .addTo(disposableBag)
    }

    fun logout() {
        userRepository.logout()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { userLiveData.value = Resource.Loading() }
            .subscribe(
                { userLiveData.value = Resource.Success() },
                { userLiveData.value = Resource.Failure() }
            )
            .addTo(disposableBag)
    }

}