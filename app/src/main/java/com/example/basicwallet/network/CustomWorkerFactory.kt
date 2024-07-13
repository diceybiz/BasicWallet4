package com.example.basicwallet.network

import android.content.Context
import androidx.work.ListenableWorkerFactory
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters

class CustomWorkerFactory(private val context: Context) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            CustomerWorker::class.java.name -> {
                CustomerWorker(
                    appContext,
                    workerParameters,
                    CustomerSearchRepository(RetrofitInstance.getInstance(context).api)
                )
            }
            else -> null
        }
    }
}