package com.example.basicwallet.ui.theme

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asFlow
import androidx.recyclerview.widget.RecyclerView
import com.example.basicwallet.R
import com.example.basicwallet.adapter.CustomerAdapter
import com.example.basicwallet.databinding.FragmentWalletBinding
import com.example.basicwallet.viewmodel.WalletViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.Flow
import com.example.basicwallet.model.Customer

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Wallet Balance: $balance",
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(16.dp))
            // EditText for search input
            var searchQuery by remember { mutableStateOf("") }
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Button for search action
            Button(
                onClick = { /* Handle search action */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Search")
            }
            Spacer(modifier = Modifier.height(16.dp))
            // RecyclerView for displaying customer list
            LazyColumn {
                items(/* List of customers */) { customer ->
                    Text(text = customer.name)
                }
            }
        }
    }

    binding.customerRecyclerView.apply {
        layoutManager = LinearLayoutManager(context)
        adapter = customerAdapter
    }

    lifecycleScope.launch {
        viewModel.customerDataState.asFlow().collect { customers: List<Customer> ->

            customerAdapter.submitList(customers)
        }
    }

    binding.searchButton.setOnClickListener {
        val query = binding.searchEditText.text.toString()
        viewModel.searchCustomers(query)
    }
}
}

@Composable

fun WalletScreenContent(balance: String, customers: List<Customer>) {
    val balance by walletViewModel.balance.observeAsState()
    val searchQuery = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            label = { Text("Search") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* Handle search */ }) {
            Text("Search")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(walletViewModel.customers) { customer ->
                Text(text = customer.name)
            }
        }
    }
}

