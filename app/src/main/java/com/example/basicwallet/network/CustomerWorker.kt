package com.example.basicwallet.network

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.basicwallet.service.CustomerSearchRepository

class CustomerWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val repository: CustomerSearchRepository
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // Implement the work to be done here
        return Result.success()
    }
}

