package com.example.basicwallet

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clover.sdk.util.CloverAccount
import com.example.basicwallet.connector.CustomCustomerConnector
import com.example.basicwallet.connector.MerchantConnector
import com.example.basicwallet.network.NetworkClient
import com.example.basicwallet.network.WooCommerceApiClient
import com.example.basicwallet.ui.theme.BasicWalletTheme
import com.example.basicwallet.ui.theme.WalletScreen
import com.example.basicwallet.ui.theme.WalletScreenContent
import com.example.basicwallet.viewmodel.WalletViewModel
import com.example.basicwallet.viewmodel.ErrorType
import com.clover.sdk.v3.customers.Customer as CustomerV3
import com.clover.sdk.v3.customers.CustomerMetadata
import java.util.concurrent.Executors
import com.example.basicwallet.service.CustomerSearchService
import com.example.basicwallet.service.MerchantService
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import kotlinx.coroutines.launch
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    private lateinit var walletViewModel: WalletViewModel
    private var customCustomerConnector: CustomCustomerConnector? = null
    private var merchantConnector: MerchantConnector? = null
    private var currentCustomer: CustomerV3? = null
    private lateinit var wooCommerceApiClient: WooCommerceApiClient
    private lateinit var merchantService: MerchantService
    private lateinit var customerSearchService: CustomerSearchService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val networkClient = NetworkClient("https://dicey.biz/wp-json/wc/v3/")
        wooCommerceApiClient = WooCommerceApiClient(networkClient, "ck_fd49704c7f0abb0d51d8f410fc6aa5a3d0ca10e9", "cs_c15cb676dc137fd0a2d30b8b711f7ff5107e31cb")

        walletViewModel = ViewModelProvider(this).get(WalletViewModel::class.java)

        val account = CloverAccount.getAccount(this)

        if (account != null) {
            Timber.i("Clover account found: ${account.name}")
            merchantService = MerchantService(walletViewModel)
            customerSearchService = CustomerSearchService(merchantService)
            walletViewModel = ViewModelProvider(this).get(WalletViewModel::class.java)
        } else {
            Timber.e("No Clover account found. Please ensure the account is set on the device.")
        }

        walletViewModel = ViewModelProvider(this).get(WalletViewModel::class.java)

        setContent {
            BasicWalletTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val balance = walletViewModel.balance.value ?: "0"
                    WalletScreenContent(balance = balance)
                }
            }
        }
        observeViewModel()

    }


    private fun observeViewModel() {
        walletViewModel.balance.observe(this, { balance ->
            // Handle balance update
        })
    }
    private fun onSearchCustomer(phone: String) {
        walletViewModel.searchCustomer(phone)
    }

    private fun onSaveBalance() {
        val customer = currentCustomer
        if (customer != null) {
            lifecycleScope.launch {
                try {
                    val metadata = CustomerMetadata().apply {
                        note = "Wallet Balance: ${walletViewModel.balance.value}"
                    }
                    customer.metadata = metadata
                    // Save the updated customer data back to the server
                    // Implement the code to update the customer on the server
                } catch (e: Exception) {
                    Timber.e("Failed to save balance: ${e.message}")
                    walletViewModel.setError(ErrorType.UnknownError(e.localizedMessage))
                }
            }
        }
    }
}
