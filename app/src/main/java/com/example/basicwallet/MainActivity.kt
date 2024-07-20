package com.example.basicwallet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.clover.sdk.util.CloverAccount
import com.example.basicwallet.network.NetworkClient
import com.example.basicwallet.network.WooCommerceApiClient
import com.example.basicwallet.ui.theme.WalletScreenContent
import com.example.basicwallet.ui.theme.BasicWalletTheme
import com.example.basicwallet.viewmodel.WalletViewModel
import com.example.basicwallet.viewmodel.ErrorType
import kotlinx.coroutines.launch
import timber.log.Timber
import com.example.basicwallet.service.CustomerSearchServiceImpl
import com.example.basicwallet.viewmodel.WalletViewModelFactory
import com.clover.sdk.v3.customers.CustomerMetadata
import com.example.basicwallet.service.ServiceFactory
import com.example.basicwallet.network.CustomerSearchRepository

class MainActivity : AppCompatActivity() {
    private lateinit var walletViewModel: WalletViewModel
    private lateinit var wooCommerceApiClient: WooCommerceApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
            setContent {
                BasicWalletTheme {
                    // Observe the customer data state
                    val customers by walletViewModel.customerDataState.observeAsState(emptyList())
                    // Pass the list of customers to the WalletScreenContent composable function
                    WalletScreenContent(balance = walletViewModel.balance.value?.toString() ?: "0", customers = customers)
                }
            }
        */
        // Initialize NetworkClient and WooCommerceApiClient
        val networkClient = NetworkClient("https://dicey.biz/wp-json/wc/v3/")
        wooCommerceApiClient = WooCommerceApiClient(networkClient, "ck_fd49704c7f0abb0d51d8f410fc6aa5a3d0ca10e9", "cs_c15cb676dc137fd0a2d30b8b711f7ff5107e31cb")

        // Initialize WalletViewModel
        val customerSearchService = ServiceFactory.createCustomerSearchService()
        val factory = WalletViewModelFactory(application, customerSearchService)
        walletViewModel = ViewModelProvider(this, factory).get(WalletViewModel::class.java)

        // Check for Clover account
        val account = CloverAccount.getAccount(this)
        if (account != null) {
            Timber.i("Clover account found: ${account.name}")
        } else {
            Timber.e("No Clover account found. Please ensure the account is set on the device.")
        }

        // Set up the UI
        setContent {
            BasicWalletTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val customers by walletViewModel.customerDataState.observeAsState(emptyList())

                    WalletScreenContent(balance = walletViewModel.balance.value?.toString() ?: "0", customers = customers)
                }
            }
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        walletViewModel.balance.observe(this, Observer { balance ->
            // Update UI or perform actions based on the balance change
        })
    }

    private fun onSearchCustomer(phone: String) {
        walletViewModel.searchCustomer(phone)
    }

    private fun onSaveBalance() {
        val customer = walletViewModel.currentCustomer.value
        if (customer != null) {
            lifecycleScope.launch {
                try {
                    val tokenResponse = wooCommerceApiClient.authenticate()
                    if (tokenResponse.isSuccessful) {
                        val token = tokenResponse.body()?.token ?: ""
                        val updateResponse = wooCommerceApiClient.updateCustomerStoreCreditBalance(
                            token,
                            customer.id.toInt(),
                            walletViewModel.balance.value?.toInt() ?: 0
                        )
                        if (updateResponse.isSuccessful) {
                            Timber.i("Balance saved successfully")
                        } else {
                            Timber.e("Failed to save balance: ${updateResponse.errorBody()?.string()}")
                            walletViewModel.setError(ErrorType.UnknownError("Failed to save balance"))
                        }
                    } else {
                        Timber.e("Failed to authenticate: ${tokenResponse.errorBody()?.string()}")
                        walletViewModel.setError(ErrorType.UnknownError("Failed to authenticate"))
                    }
                } catch (e: Exception) {
                    Timber.e("Failed to save balance: ${e.message}")
                    walletViewModel.setError(ErrorType.UnknownError(e.localizedMessage ?: "Unknown error"))
                }
            }
        }
    }
}

