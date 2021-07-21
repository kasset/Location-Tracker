package com.gmail.assetkikbayev.locations.model.repositories

import com.gmail.assetkikbayev.locations.model.authentification.RemoteAuthSource
import com.gmail.assetkikbayev.locations.model.data.RemoteDataSource
import com.gmail.assetkikbayev.locations.model.locations.LocationStorage
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MapsRepositoryImpl @Inject constructor(
    private val firebaseAuth: RemoteAuthSource,
    private val locations: LocationStorage,
) : MapsRepository {
    override fun getLocation(date: String): Observable<Map<String, Any>> =
        locations.getUserLocation(date)

    override fun logout(): Completable = firebaseAuth.logout()
}