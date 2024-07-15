package com.example.basicwallet.model

data class Merchant(val id: Int, val name: String) {
    fun isValid(): Boolean {
        return id > 0 && name.isNotEmpty()
    }
}
