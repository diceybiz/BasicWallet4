package com.example.basicwallet.service

import com.example.basicwallet.network.CustomerSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CustomerSearchServiceImpl : CustomerSearchService {
    private val repository = CustomerSearchRepository()

    override suspend fun searchCustomer(phone: String): CustomerSearchResponse {
        return repository.searchCustomer(phone)
    }

    override suspend fun searchCustomers(query: String): CustomerSearchResponse {
        return repository.searchCustomers(query)
    }
}