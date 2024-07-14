package com.example.basicwallet.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicwallet.network.CustomerSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import com.example.basicwallet.service.CustomerSearchResponse
import com.example.basicwallet.service.CustomerSearchService
import com.example.basicwallet.model.Customer


sealed class ErrorType {
    object NetworkError : ErrorType()
    object InvalidInputError : ErrorType()
    data class UnknownError(val message: String) : ErrorType()
}

class WalletViewModel : ViewModel() {
    val balance = MutableLiveData<String>()

    private val customerSearchRepository = CustomerSearchRepository(CustomerSearchService())

    private val _balanceState = MutableStateFlow(0)
    val balanceState: StateFlow<Int> get() = _balanceState

    private val _errorState = MutableStateFlow<ErrorType?>(null)
    val errorState: StateFlow<ErrorType?> get() = _errorState

    private val _customerDataState = MutableStateFlow<Customer?>(null)
    val customerDataState: StateFlow<Customer?> get() = _customerDataState

    private val _isLoadingState = MutableStateFlow(false)
    val isLoadingState: StateFlow<Boolean> get() = _isLoadingState

    fun setBalance(newBalance: Int) {
        _balanceState.value = newBalance
    }

    fun setError(error: ErrorType) {
        _errorState.value = error
    }

    fun setCustomerData(customer: Customer) {
        _customerDataState.value = customer
    }

    fun setLoadingState(isLoading: Boolean) {
        _isLoadingState.value = isLoading
    }

    fun searchCustomer(phone: String) {
        viewModelScope.launch {
            _isLoadingState.value = true
            customerSearchRepository.searchCustomer(phone).collect { response ->
                when (response) {
                    is CustomerSearchResponse -> {
                        setCustomerData(response.customers.firstOrNull())
                        setError(null)
                    }
                    else -> {
                        setCustomerData(null)
                        setError(ErrorType.NetworkError)
                    }
                }
                _isLoadingState.value = false
            }
        }
    }

    companion object {
        private var instance: WalletViewModel? = null

        fun getInstance(application: Application): WalletViewModel {
            if (instance == null) {
                instance = WalletViewModel(application)
            }
            return instance!!
        }
    }
}