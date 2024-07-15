package com.example.basicwallet.service

import com.example.basicwallet.network.RetrofitInstance

class CustomerSearchServiceImpl(private val merchantService: MerchantService) {

    private val api: CustomerSearchService = RetrofitInstance.getInstance().api

    suspend fun searchCustomer(phone: String): CustomerSearchResponse {
        return api.searchCustomer(phone)
    }
}
