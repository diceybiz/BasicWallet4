package com.example.basicwallet.connector

import android.util.Log
import android.content.Context
import android.os.IInterface
import com.clover.sdk.util.CloverAccount
import com.clover.sdk.v1.ServiceConnector
import com.clover.sdk.v1.customer.CustomerConnector
import com.example.basicwallet.service.CustomerSearchServiceImpl
import com.example.basicwallet.service.CustomerSearchResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomCustomerConnector(
    context: Context,
    private val customerSearchService: CustomerSearchServiceImpl
) : ServiceConnector.OnServiceConnectedListener {
    private var customerConnector: CustomerConnector? = null

    init {
        val account = CloverAccount.getAccount(context)
        customerConnector = CustomerConnector(context, account, this)
        customerConnector?.connect()
    }

    override fun onServiceConnected(p0: ServiceConnector<out IInterface>?) {
        Log.i("CustomCustomerConnector", "CustomerConnector connected")
    }

    override fun onServiceDisconnected(p0: ServiceConnector<out IInterface>?) {
        Log.i("CustomCustomerConnector", "CustomerConnector disconnected")
    }

    fun searchCustomer(phone: String) {
        CoroutineScope(Dispatchers.IO).launch {
            customerSearchService.searchCustomer(phone)
        }
    }

    fun disconnect() {
        customerConnector?.disconnect()
    }
}