package com.example.basicwallet.ui.theme

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

import androidx.compose.runtime.*
import androidx.compose.material3.*

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.TextFieldValue




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


}
}

@Composable

fun WalletScreenContent(balance: String, customers: List<Customer>) {

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

            items(customers) { customer ->
                Text(text = customer.name)
            }
        }
    }
}

