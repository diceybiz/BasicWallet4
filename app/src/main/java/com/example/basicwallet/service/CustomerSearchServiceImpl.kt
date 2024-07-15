package com.example.basicwallet.service

import com.example.basicwallet.network.CustomerSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CustomerSearchServiceImpl(private val repository: CustomerSearchRepository) : CustomerSearchService {

    override suspend fun searchCustomer(phone: String): Flow<CustomerSearchResponse> {
        return flow {
            emit(repository.searchCustomer(phone))
        }
    }

    override suspend fun searchCustomers(query: String): Flow<CustomerSearchResponse> {
        return flow {
            emit(repository.searchCustomers(query))
        }
    }
}