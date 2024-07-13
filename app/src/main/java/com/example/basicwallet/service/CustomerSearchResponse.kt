package com.example.basicwallet.service

data class CustomerSearchResponse(
    val customers: List<Customer>
)

data class Customer(
    val id: String,
    val name: String,
    val phone: String
)