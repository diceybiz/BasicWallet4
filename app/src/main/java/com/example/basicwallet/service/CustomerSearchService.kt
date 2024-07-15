package com.example.basicwallet.service

import retrofit2.http.GET
import retrofit2.http.Query

interface CustomerSearchService {
    @GET("customers")
    suspend fun searchCustomers(
        @Query("search") query: String
    ): CustomerSearchResponse

    @GET("customers")
    suspend fun searchCustomer(
        @Query("search") phone: String
    ): CustomerSearchResponse
}
