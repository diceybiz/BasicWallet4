package com.example.basicwallet.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basicwallet.service.CustomerSearchService

class WalletViewModelFactory(
    private val application: Application,
    private val customerSearchService: CustomerSearchService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WalletViewModel::class.java)) {
            return WalletViewModel(application, customerSearchService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
