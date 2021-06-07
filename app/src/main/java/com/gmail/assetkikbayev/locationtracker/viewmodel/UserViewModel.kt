package com.gmail.assetkikbayev.locationtracker.viewmodel

import com.gmail.assetkikbayev.locationtracker.model.repositories.UserRepositoryImpl
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl
) : BaseViewModel() {
    fun saveLocation() {
        userRepository.saveLocation()
    }

    fun logout() {
        userRepository.logout()
    }
}