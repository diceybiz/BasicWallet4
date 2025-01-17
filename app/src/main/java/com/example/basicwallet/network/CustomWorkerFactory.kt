package com.example.basicwallet.network

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.basicwallet.viewmodel.WalletViewModel
import com.example.basicwallet.network.CustomerSearchRepository

class CustomWorkerFactory(private val walletViewModel: WalletViewModel) : WorkerFactory() {

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
                    CustomerSearchRepository(RetrofitInstance.customerSearchService)
                )
            }
            else -> null
        }
    }
}