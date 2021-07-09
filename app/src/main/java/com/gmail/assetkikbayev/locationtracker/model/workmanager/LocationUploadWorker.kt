package com.gmail.assetkikbayev.locationtracker.model.workmanager

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.gmail.assetkikbayev.locationtracker.model.repositories.UserRepositoryImpl
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class LocationUploadWorker @Inject constructor(
    context: Context,
    workerParams: WorkerParameters,
    private val userRepo: UserRepositoryImpl,
) : RxWorker(context, workerParams) {
    override fun createWork(): Single<Result> {
        return userRepo.transferDataToRemoteFromDB()
            .observeOn(AndroidSchedulers.mainThread())
            .andThen(Single.just(Result.success()))
            .onErrorReturnItem(Result.failure())
    }
}

