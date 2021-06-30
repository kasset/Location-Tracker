package com.gmail.assetkikbayev.locationtracker.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel() : ViewModel() {

    protected val disposableBag = CompositeDisposable()

    override fun onCleared() {
        disposableBag.dispose()
        super.onCleared()
    }
}