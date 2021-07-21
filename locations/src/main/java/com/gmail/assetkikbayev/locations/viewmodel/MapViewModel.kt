package com.gmail.assetkikbayev.locations.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.assetkikbayev.locations.model.repositories.MapsRepository
import com.gmail.assetkikbayev.locations.utils.Resource
import com.gmail.assetkikbayev.locations.utils.SingleLiveEvent
import com.gmail.assetkikbayev.locations.utils.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val mapsRepository: MapsRepository
) : BaseViewModel() {

    private val mapLiveData = MutableLiveData<Map<String, Any>>()
    private val mapSingleLiveEvent = SingleLiveEvent<Resource<Nothing>>()

    val getMapLiveData: LiveData<Map<String, Any>>
        get() = mapLiveData
    val getMapSingleEvent: LiveData<Resource<Nothing>>
        get() = mapSingleLiveEvent

    fun getLocations(date: String) {
        mapsRepository.getLocation(date)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data -> mapLiveData.value = data },
                {}
            )
            .addTo(disposableBag)
    }

    fun logout() {
        mapsRepository.logout()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mapSingleLiveEvent.value = Resource.Loading() }
            .subscribe(
                { mapSingleLiveEvent.value = Resource.Success() },
                { mapSingleLiveEvent.value = Resource.Failure() }
            )
            .addTo(disposableBag)
    }
}