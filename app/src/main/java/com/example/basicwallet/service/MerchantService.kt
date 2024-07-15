package com.example.basicwallet.service

import com.example.basicwallet.model.Merchant
import com.example.basicwallet.viewmodel.WalletViewModel

class MerchantService(private val walletViewModel: WalletViewModel) {
    fun fetchMerchantInfo(merchant: Merchant?) {
        merchant?.let {
            if (it.isValid()) {
                android.util.Log.i("MerchantService", "Fetched merchant info: ${it.name} (ID: ${it.id})")
                walletViewModel.setMerchant(it)
            } else {
                android.util.Log.e("MerchantService", "Invalid merchant object: $merchant")
                // Handle invalid merchant object error
            }
        } ?: run {
            android.util.Log.e("MerchantService", "Merchant object is null")
            // Handle null merchant object error
        }
    }
}
