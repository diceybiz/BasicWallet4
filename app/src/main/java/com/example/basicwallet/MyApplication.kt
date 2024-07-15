package com.example.basicwallet

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.basicwallet.network.CustomWorkerFactory
import com.example.basicwallet.viewmodel.WalletViewModel
import com.example.basicwallet.viewmodel.WalletViewModelFactory
import com.example.basicwallet.service.CustomerSearchServiceImpl

class MyApplication : Application() {

    private lateinit var walletViewModel: WalletViewModel

    override fun onCreate() {
        super.onCreate()
        val customerSearchService = CustomerSearchServiceImpl()
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

/*

class MyApplication : Application() {
    private val walletViewModel = WalletViewModel.getInstance(this)

    override fun onCreate() {
        super.onCreate()
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

class WalletViewModel private constructor(private val application: Application) {
    // ...

    companion object {
        private var instance: WalletViewModel? = null

        fun getInstance(application: Application): WalletViewModel {
            if (instance == null) {
                instance = WalletViewModel(application)
            }
            return instance!!
        }
    }
}
*/