package com.gmail.assetkikbayev.locations.model.locations

import com.gmail.assetkikbayev.locations.model.data.RemoteDataSource
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationStorage @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun getUserLocation(date: String): Observable<Map<String, Any>> =
        remoteDataSource.getLocation()
            .subscribeOn(Schedulers.io())
            .filter { documentSnapshot ->
                documentSnapshot.id.trim().substring(0, 10) == date
            }
            .flatMap { documentSnapshot -> Observable.just(documentSnapshot.data) }
}
