package com.example.basicwallet.ui.theme

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment


import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.text.BasicTextField


import androidx.compose.ui.tooling.preview.Preview
import com.example.basicwallet.api.ApiClient
import com.example.basicwallet.api.WooCommerceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.basicwallet.R
import com.example.basicwallet.adapter.CustomerAdapter
import com.example.basicwallet.databinding.FragmentWalletBinding
import com.example.basicwallet.model.Customer
import com.example.basicwallet.viewmodel.WalletViewModel




class WalletScreen : Fragment(R.layout.fragment_wallet) {

    private lateinit var binding: FragmentWalletBinding
    private lateinit var viewModel: WalletViewModel
    private lateinit var customerAdapter: CustomerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWalletBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(WalletViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel


    }


}


@Composable

fun WalletScreenContent(balance: String, customers: List<Customer>) {


    val searchQuery = remember { mutableStateOf("") }

    val balanceState = remember { mutableStateOf(balance) }
    val filteredCustomers = remember { mutableStateOf(customers) }


    val version = "1.0.0" // Replace with actual version
    val buildDateTime = "2023-10-01 12:00:00" // Replace with actual build date/time

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Balance: ${balanceState.value}")
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            label = { Text("Search") }
        )
        Spacer(modifier = Modifier.height(8.dp))


        Button(onClick = {
            // Handle search
            val query = searchQuery.value
            println("Search button clicked with query: $query")
            // Perform search operation
            // For example, filter the customers list based on the query

            filteredCustomers.value = customers.filter { it.name.contains(query, ignoreCase = true) }
            // Update the UI with the filtered customers
            // This is a placeholder for actual search logic

            // Fetch balance from WooCommerce API
            fetchBalance { balance ->
                balanceState.value = balance
            }
        }) {
            Text("Search")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Version: $version")
        Text(text = "Build Date/Time: $buildDateTime")
        LazyColumn {


            items(filteredCustomers.value) { customer ->
                Text(text = customer.name)
            }
        }
    }
}

