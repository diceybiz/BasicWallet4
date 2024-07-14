package com.example.basicwallet.connector

import android.util.Log
import android.content.Context
import android.accounts.Account
import com.clover.sdk.v1.merchant.MerchantConnector
import com.clover.sdk.v1.ServiceConnector
import com.example.basicwallet.service.MerchantService

class MerchantConnector(
    private val context: Context,
    private val account: Account,
    private val merchantService: MerchantService
) {
    private val merchantConnector: MerchantConnector

    init {
        merchantConnector = MerchantConnector(context, account, object : ServiceConnector.OnServiceConnectedListener {
            override fun onServiceConnected(connector: ServiceConnector<*>) {
                Log.i("MerchantConnector", "Merchant service connected")
                fetchMerchantInfo()
            }

            override fun onServiceDisconnected(connector: ServiceConnector<*>) {
                Log.i("MerchantConnector", "Merchant service disconnected")
            }
        })
        merchantConnector.connect()
    }

    fun disconnect() {
        merchantConnector.disconnect()
    }

    private fun fetchMerchantInfo() {
        val cloverMerchant = merchantConnector.merchant
        val merchant = com.example.basicwallet.service.Merchant(cloverMerchant.id.toInt(), cloverMerchant.name)
        merchantService.fetchMerchantInfo(merchant)
    }
}