package com.gmail.assetkikbayev.locationtracker.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


abstract class BaseViewModel() : ViewModel() {

    protected val disposableBag = CompositeDisposable()

    override fun onCleared() {
        disposableBag.dispose()
        super.onCleared()
    }
}