package com.example.basicwallet.service

import com.example.basicwallet.network.CustomerSearchRepository
import kotlinx.coroutines.flow.firstOrNull

class CustomerSearchServiceImpl(private val repository: CustomerSearchRepository) : CustomerSearchService {

    override suspend fun searchCustomer(phone: String): CustomerSearchResponse {
        return repository.searchCustomer(phone).firstOrNull() ?: throw Exception("No customer found")
    }

    override suspend fun searchCustomers(query: String): CustomerSearchResponse {
        return repository.searchCustomers(query).firstOrNull() ?: throw Exception("No customers found")
    }
}