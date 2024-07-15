package com.example.basicwallet

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.basicwallet.network.CustomWorkerFactory
import com.example.basicwallet.viewmodel.WalletViewModel
import com.example.basicwallet.viewmodel.WalletViewModelFactory
import com.example.basicwallet.service.CustomerSearchServiceImpl
import com.example.basicwallet.network.CustomerSearchRepository

class MyApplication : Application() {

    private lateinit var walletViewModel: WalletViewModel

    override fun onCreate() {
        super.onCreate()
        val customerSearchRepository = CustomerSearchRepository(CustomerSearchServiceImpl())
        val customerSearchService = CustomerSearchServiceImpl(customerSearchRepository)
        val factory = WalletViewModelFactory(this, customerSearchService)
        walletViewModel = factory.create(WalletViewModel::class.java)
        WorkManagerInitializer.initialize(this, Configuration.Builder()
            .setWorkerFactory(CustomWorkerFactory(walletViewModel))
            .build())
    }
}

class WorkManagerInitializer {
    companion object {
        fun initialize(application: Application, configuration: Configuration) {
            WorkManager.initialize(application, configuration)
        }
    }
}