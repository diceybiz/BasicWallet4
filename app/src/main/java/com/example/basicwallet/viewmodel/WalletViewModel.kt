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

    private val _balance = MutableLiveData<String>()
    val balance: LiveData<String> get() = _balance

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

/*
sealed class ErrorType {
    object NetworkError : ErrorType()
    object InvalidInputError : ErrorType()
    data class UnknownError(val message: String) : ErrorType()
}

class WalletViewModel private constructor(application: Application) : ViewModel() {
    val balance = MutableStateFlow("0")

    private val customerSearchRepository = CustomerSearchRepository(CustomerSearchService())

    private val _balanceState = MutableStateFlow(0)
    val balanceState: StateFlow<Int> get() = _balanceState

    private val _errorState = MutableStateFlow<ErrorType?>(null)
    val errorState: StateFlow<ErrorType?> get() = _errorState

    private val _customerDataState = MutableStateFlow<CloverCustomer?>(null)
    val customerDataState: StateFlow<CloverCustomer?> get() = _customerDataState

    private val _isLoadingState = MutableStateFlow(false)
    val isLoadingState: StateFlow<Boolean> get() = _isLoadingState

    fun setBalance(newBalance: String) {
        balance.value = newBalance
    }

    fun setError(error: ErrorType?) {
        _errorState.value = error
    }

    fun setCustomerData(customer: CloverCustomer?) {
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
class WalletViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WalletViewModel::class.java)) {
            return WalletViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
*/
