package com.example.basicwallet.network

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class NetworkClient {
    val retrofit: Retrofit

    init {
        val client: OkHttpClient = Builder()
            .certificatePinner(
                Builder()
                    .add(
                        "dicey.biz",
                        "sha256/c5a51df13e43b9fc9006c96b33bc7506c4e5d3fa555a247f1768a86607e09738"
                    )
                    .build()
            )
            .build()

        // Create an error handler for certificate pinning errors
        client.setCertificatePinnerErrorHandler(CertificatePinnerErrorHandler())

        retrofit = Retrofit.Builder()
            .baseUrl("https://dicey.biz")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private class CertificatePinnerErrorHandler {
        fun onCertificatePinningError(e: CertificatePinnerException) {
            // Handle the error here, e.g., log the error, show an error message to the user, etc.
            System.out.println("Certificate pinning error: " + e.getMessage())
        }
    }
}