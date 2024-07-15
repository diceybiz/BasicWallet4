package com.example.basicwallet.network

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.basicwallet.service.CustomerSearchService

object RetrofitInstance {
    private val retrofit: Retrofit by lazy {
        val certificatePinner = CertificatePinner.Builder()
            .add("dicey.biz", "sha256/c5a51df13e43b9fc9006c96b33bc7506c4e5d3fa555a247f1768a86607e09738")
            .build()

        val client = OkHttpClient.Builder()
            .certificatePinner(certificatePinner)
            .build()

        Retrofit.Builder()
            .baseUrl("https://dicey.biz")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val customerSearchService: CustomerSearchService by lazy {
        retrofit.create(CustomerSearchService::class.java)
    }
}