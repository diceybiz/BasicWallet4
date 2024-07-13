package com.example.basicwallet.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

class WooCommerceApiClient(private val networkClient: NetworkClient, private val consumerKey: String, private val consumerSecret: String) {
    private val wooCommerceApi: WooCommerceApi = networkClient.getRetrofit().create(WooCommerceApi::class.java)

    fun authenticate(): Call<TokenResponse> {
        val request = AuthenticateRequest(consumerKey, consumerSecret)
        return wooCommerceApi.authenticate(request)
    }

    fun updateCustomerStoreCreditBalance(token: String, customerId: Int, newStoreCreditBalance: Int): Call<Unit> {
        val request = UpdateCustomerRequest(newStoreCreditBalance)
        return wooCommerceApi.updateCustomerStoreCreditBalance(token, customerId, request)
    }
}

interface WooCommerceApi {
    @POST("auth/token")
    fun authenticate(@Body body: AuthenticateRequest): Call<TokenResponse>

    @PUT("customers/{customer_id}")
    fun updateCustomerStoreCreditBalance(@Header("Authorization") token: String, @Path("customer_id") customerId: Int, @Body body: UpdateCustomerRequest): Call<Unit>
}

data class AuthenticateRequest(val consumerKey: String, val consumerSecret: String)
data class TokenResponse(val token: String)
data class UpdateCustomerRequest(val storeCredit: Int)