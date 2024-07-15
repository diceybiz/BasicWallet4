package com.example.basicwallet.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.basicwallet.service.CustomerSearchResponse
import com.example.basicwallet.service.CustomerSearchService
import com.example.basicwallet.model.Customer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CustomerSearchRepository(private var customerSearchService: CustomerSearchService? = null) {

    fun setService(service: CustomerSearchService) {
        this.customerSearchService = service
    }

    suspend fun searchCustomer(phone: String): Flow<CustomerSearchResponse> {
        return flow {
            val response = withContext(Dispatchers.IO) {
                customerSearchService?.searchCustomer(phone)
            }
            emit(response)
        }
    }

    suspend fun searchCustomers(query: String): Flow<CustomerSearchResponse> {
        return flow {
            val response = withContext(Dispatchers.IO) {
                customerSearchService?.searchCustomers(query)
            }
            emit(response)
        }
    }
}

