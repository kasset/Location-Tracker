package com.gmail.assetkikbayev.locations.model.repositories

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable


interface MapsRepository {
    fun getLocation(dayOfMonth: Int, month: Int, year: Int): Flowable<Map<String, Any>>
    fun logout(): Completable
}