package com.gmail.assetkikbayev.locationtracker.model.workmanager

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.reactivex.Single

class LocationUploadWorker(
    context: Context,
    workerParams: WorkerParameters
) : RxWorker(context, workerParams) {
    override fun createWork(): Single<Result> {
        TODO("Not yet implemented")
    }
}