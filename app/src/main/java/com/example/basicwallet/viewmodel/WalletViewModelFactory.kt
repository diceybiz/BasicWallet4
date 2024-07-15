package com.example.basicwallet.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WalletViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WalletViewModel::class.java)) {
            return WalletViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
