package com.gmail.assetkikbayev.locationtracker.model.repositories

interface UserRepository {
    fun saveLocation();
    fun logout()
}