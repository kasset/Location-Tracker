package com.gmail.assetkikbayev.locationtracker.viewmodel

import androidx.lifecycle.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val disposableBag = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        disposableBag.add(disposable)
    }

    fun removeDisposable(disposable: Disposable) {
        disposableBag.remove(disposable)
    }

    override fun onCleared() {
        disposableBag.dispose()
        super.onCleared()
    }
}