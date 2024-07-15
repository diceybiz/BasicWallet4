package com.example.basicwallet.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.basicwallet.network.CustomerSearchRepository
import com.example.basicwallet.service.CustomerSearchResponse
import com.example.basicwallet.service.CustomerSearchService
import com.example.basicwallet.model.Merchant
import com.example.basicwallet.model.Customer
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WalletViewModel(application: Application, private val customerSearchService: CustomerSearchService) : AndroidViewModel(application) {
    private val repository = CustomerSearchRepository(customerSearchService)

    private val _balance = MutableLiveData<Int>()
    val balance: LiveData<Int> get() = _balance

    private val _currentCustomer = MutableLiveData<Customer?>()
    val currentCustomer: LiveData<Customer?> get() = _currentCustomer

    private val _customerDataState = MutableLiveData<List<Customer>>()
    val customerDataState: LiveData<List<Customer>> get() = _customerDataState

    fun searchCustomers(query: String) {
        viewModelScope.launch {
            val customers = repository.searchCustomers(query).firstOrNull()
            _customerDataState.value = customers?.customers?.map { it.toModel() } ?: emptyList()
        }
    }

    fun searchCustomer(phone: String) {
        viewModelScope.launch {
            val customer = repository.searchCustomer(phone).firstOrNull()
            _currentCustomer.value = customer?.customers?.firstOrNull()?.toModel()
        }
    }

    fun setError(error: ErrorType) {
        // Handle error
    }

    fun setMerchant(merchant: Merchant) {
        // Set merchant
    }
}

sealed class ErrorType {
    object NetworkError : ErrorType()
    data class UnknownError(val message: String) : ErrorType()
}

// Extension function to convert service Customer to model Customer
fun com.example.basicwallet.service.Customer.toModel(): com.example.basicwallet.model.Customer {
    return com.example.basicwallet.model.Customer(
        id = this.id,
        name = this.name,
        phone = this.phone
    )
}