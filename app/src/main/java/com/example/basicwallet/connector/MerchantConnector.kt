package com.example.basicwallet.connector

import android.util.Log
import android.content.Context
import android.accounts.Account
import com.clover.sdk.util.CloverAccount
import com.clover.sdk.v1.merchant.MerchantConnector
import com.clover.sdk.v1.merchant.Merchant
import com.clover.sdk.v1.ServiceConnector
import com.example.basicwallet.service.MerchantService

class CustomMerchantConnector(context: Context, private val merchantService: MerchantService) : ServiceConnector.OnServiceConnectedListener {
    private var merchantConnector: MerchantConnector? = null

    init {
        val account = CloverAccount.getAccount(context)
        merchantConnector = MerchantConnector(context, account, this)
        merchantConnector?.connect()
    }

    override fun onServiceConnected(p0: ServiceConnector<*>) {
        Log.i("CustomMerchantConnector", "MerchantConnector connected")
        fetchMerchant()
    }

    override fun onServiceDisconnected(p0: ServiceConnector<*>) {
        Log.i("CustomMerchantConnector", "MerchantConnector disconnected")
    }

    fun fetchMerchant() {
        try {
            val merchant = merchantConnector?.merchant
            merchantService.fetchMerchantInfo(merchant?.let { com.example.basicwallet.model.Merchant(it.id.toInt(), it.name) })
        } catch (e: Exception) {
            Log.e("CustomMerchantConnector", "Failed to fetch merchant info: ${e.message}")
        }
    }

    fun disconnect() {
        merchantConnector?.disconnect()
    }
}