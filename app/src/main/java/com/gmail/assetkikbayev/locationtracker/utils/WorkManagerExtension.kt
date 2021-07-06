package com.gmail.assetkikbayev.locationtracker.utils

import androidx.work.*

fun WorkManager.scheduleUploadLocationJob(tag: String, worker: Class<out ListenableWorker>) {
    if (!this.isWorkScheduled(tag))
        this.cancelAllWorkByTag(tag)
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()
    val oneTimeRequest =
        OneTimeWorkRequest.Builder(worker)
            .setConstraints(constraints)
            .addTag(tag)
            .build()
    this.enqueue(oneTimeRequest)
}

fun WorkManager.isWorkScheduled(tag: String): Boolean {
    val workList = this.getWorkInfosByTag(tag).get()
    workList.forEach { workInfo ->
        if (workInfo.state == WorkInfo.State.RUNNING) {
            return true
        }
    }
    return false
}