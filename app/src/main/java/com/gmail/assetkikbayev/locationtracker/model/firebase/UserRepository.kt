package com.gmail.assetkikbayev.locationtracker.model.firebase

interface UserRepository {
    fun saveLocation();
    fun logout()
}