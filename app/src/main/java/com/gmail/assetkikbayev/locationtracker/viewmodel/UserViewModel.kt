package com.gmail.assetkikbayev.locationtracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.assetkikbayev.locationtracker.model.repositories.UserRepository
import com.gmail.assetkikbayev.locationtracker.utils.Resource
import com.gmail.assetkikbayev.locationtracker.utils.SingleLiveEvent
import com.gmail.assetkikbayev.locationtracker.utils.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    //private val userLiveData = MutableLiveData<Resource<Nothing>>()
    private val userLiveData = SingleLiveEvent<Resource<Nothing>>()

    val getUserLiveData: LiveData<Resource<Nothing>>
        get() = userLiveData

    fun saveLocation() {
        userRepository.saveLocation()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { userLiveData.value = Resource.Success() },
                { userLiveData.value = Resource.Failure(it) }
            )
            .addTo(disposableBag)
    }

    fun stopLocationUpdates() {
        userRepository.stopLocationProvider()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { userLiveData.value = Resource.Success() },
                { userLiveData.value = Resource.Failure() }
            )
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