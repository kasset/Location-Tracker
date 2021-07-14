package com.gmail.assetkikbayev.locations.model.repositories

import com.gmail.assetkikbayev.locations.model.authentification.RemoteAuthSource
import io.reactivex.Completable
import javax.inject.Inject

class MapsRepositoryImpl @Inject constructor(
    private val firebaseAuth: RemoteAuthSource
) : MapsRepository {
    override fun logout(): Completable = firebaseAuth.logout()
}