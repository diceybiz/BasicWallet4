package com.example.basicwallet.service

import com.example.basicwallet.viewmodel.WalletViewModel
import timber.log.Timber

class MerchantService(private val walletViewModel: WalletViewModel) {
    fun fetchMerchantInfo(merchant: Merchant?) {
        merchant?.let {
            if (it.isValid()) {
                android.util.Log.i("Fetched merchant info: ${it.name} (ID: ${it.id})")
                walletViewModel.setMerchant(it)
            } else {
                android.util.Log.e("Invalid merchant object: $merchant")
                // Handle invalid merchant object error
            }
        } ?: run {
            android.util.Log.e("Merchant object is null")
            // Handle null merchant object error
        }
    }
}

// Assume Merchant class has an isValid() method to check its validity
data class Merchant(val id: Int, val name: String) {
    fun isValid(): Boolean {
        // Implement validation logic here
        return id > 0 && name.isNotEmpty()
    }
}