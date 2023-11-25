package com.artpit.android.servicestest

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class MyWorker(
    context: Context,
    private val workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    companion object {
        const val NAME = "MyWorker"
        private const val PAGE = "page"

        fun makeRequest(page: Int) = OneTimeWorkRequestBuilder<MyWorker>()
            .setInputData(workDataOf(PAGE to page))
            .setConstraints(makeConstraints())
            .build()

        private fun makeConstraints() = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()
    }
    override fun doWork(): Result {
        log("doWork")
        val page = workerParameters.inputData.getInt(PAGE, 0)
        for (i in 0..<5) {
            Thread.sleep(1000)
            log("Timer $i $page")
        }

        return Result.success()
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyWorker: $message")
    }
}