package com.gmail.assetkikbayev.locationtracker.viewmodel

import com.gmail.assetkikbayev.locationtracker.model.repositories.UserRepositoryImpl
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl
) : BaseViewModel() {

//    private val userLiveData = MutableLiveData<>()

//    val getUserLiveData: LiveData<>
//        get() = userLiveData

    fun saveLocation() {
        disposableBag.add(userRepository.saveLocation())
    }

    fun logout() {
        userRepository.logout()
    }

}