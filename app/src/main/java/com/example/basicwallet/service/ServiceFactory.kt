package com.example.basicwallet.service

import com.example.basicwallet.network.CustomerSearchRepository

object ServiceFactory {
    fun createCustomerSearchService(): CustomerSearchServiceImpl {
        val customerSearchService = CustomerSearchServiceImpl()
        val customerSearchRepository = CustomerSearchRepository(customerSearchService)
        return CustomerSearchServiceImpl(customerSearchRepository)
    }
}