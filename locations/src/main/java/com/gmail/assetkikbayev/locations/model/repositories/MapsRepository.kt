package com.gmail.assetkikbayev.locations.model.repositories

import io.reactivex.Completable


interface MapsRepository {
    fun logout(): Completable
}