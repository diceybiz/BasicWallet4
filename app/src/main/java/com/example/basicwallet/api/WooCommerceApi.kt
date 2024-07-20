package com.example.basicwallet.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface WooCommerceApi {
    @GET("customers")
    suspend fun getCustomerByPhone(@Query("phone") phone: String): List<Customer>

    companion object {
        private const val BASE_URL = "https://your-woocommerce-url.com/wp-json/wc/v3/"

        fun create(): WooCommerceApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WooCommerceApi::class.java)
        }
    }
}

/*

interface WooCommerceApi {
    @GET("wp-json/wc/v3/customers")
    fun getCustomerBalance(
        @Query("consumer_key") consumerKey: "ck_fd49704c7f0abb0d51d8f410fc6aa5a3d0ca10e9",
        @Query("consumer_secret") consumerSecret: "cs_c15cb676dc137fd0a2d30b8b711f7ff5107e31cb"
    ): Call<List<Customer>>
}
*/
