package com.gmail.assetkikbayev.locations.model.repositories

import io.reactivex.Completable
import io.reactivex.Observable


interface MapsRepository {
    fun getLocation(date: String): Observable<Map<String, Any>>
    fun logout(): Completable
}