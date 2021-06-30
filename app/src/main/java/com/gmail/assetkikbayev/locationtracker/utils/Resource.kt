package com.gmail.assetkikbayev.locationtracker.utils

sealed class Resource<out T> {
    data class Loading<T>(val partialData: T? = null) : Resource<T>()
    data class Success<T>(val data: T? = null) : Resource<T>()
    data class Failure<T>(val throwable: Throwable? = null) : Resource<T>()

    val extractData: T?
        get() = when (this) {
            is Success -> data
            is Loading -> partialData
            is Failure -> null
        }
}
