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

    private val _locationLiveData = MutableLiveData<Map<String, Any>>()
    private val _authSingleLiveEvent = SingleLiveEvent<Resource<Nothing>>()

    val locationLiveData: LiveData<Map<String, Any>>
        get() = _locationLiveData
    val authSingleEvent: LiveData<Resource<Nothing>>
        get() = _authSingleLiveEvent

    fun getLocations(dayOfMonth: Int, month: Int, year: Int) {
        mapsRepository.getLocation(dayOfMonth, month, year)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data -> _locationLiveData.value = data },
                {}
            )
            .addTo(disposableBag)
    }

    fun logout() {
        mapsRepository.logout()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _authSingleLiveEvent.value = Resource.Loading() }
            .subscribe(
                { _authSingleLiveEvent.value = Resource.Success() },
                { _authSingleLiveEvent.value = Resource.Failure() }
            )
            .addTo(disposableBag)
    }
}