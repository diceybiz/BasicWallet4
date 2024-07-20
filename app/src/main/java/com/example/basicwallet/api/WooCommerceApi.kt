package com.example.basicwallet.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WooCommerceApi {
    @GET("wp-json/wc/v3/customers")
    fun getCustomerBalance(
        @Query("consumer_key") consumerKey: "ck_fd49704c7f0abb0d51d8f410fc6aa5a3d0ca10e9",
        @Query("consumer_secret") consumerSecret: "cs_c15cb676dc137fd0a2d30b8b711f7ff5107e31cb"
    ): Call<List<Customer>>
}

