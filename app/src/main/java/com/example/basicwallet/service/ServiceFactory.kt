package com.example.basicwallet.service

import com.example.basicwallet.network.CustomerSearchRepository

object ServiceFactory {
    fun createCustomerSearchService(): CustomerSearchServiceImpl {
        val customerSearchRepository = CustomerSearchRepository()
        val customerSearchService = CustomerSearchServiceImpl(customerSearchRepository)
        customerSearchRepository.setService(customerSearchService)
        return customerSearchService
    }
}

