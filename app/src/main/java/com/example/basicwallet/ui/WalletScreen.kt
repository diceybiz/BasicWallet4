package com.example.basicwallet.ui.theme

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.RecyclerView
import com.example.basicwallet.R
import com.example.basicwallet.adapter.CustomerAdapter
import com.example.basicwallet.databinding.FragmentWalletBinding
import com.example.basicwallet.viewmodel.WalletViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

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

        customerAdapter = CustomerAdapter()
        binding.customerRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = customerAdapter
        }

        lifecycleScope.launch {
            viewModel.customerDataState.collect { customers ->
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
fun WalletScreenContent(balance: String) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Wallet Balance: $balance", style = MaterialTheme.typography.headlineSmall)
    }
}