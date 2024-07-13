package com.example.basicwallet.ui.theme

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basicwallet.R
import com.example.basicwallet.adapter.CustomerAdapter
import com.example.basicwallet.databinding.FragmentWalletBinding
import com.example.basicwallet.viewmodel.WalletViewModel
import kotlinx.coroutines.launch

class WalletScreen : Fragment(R.layout.fragment_wallet) {

    private lateinit var binding: FragmentWalletBinding
    private lateinit var viewModel: WalletViewModel
    private lateinit var customerAdapter: CustomerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWalletBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(WalletViewModel::class.java)

        customerAdapter = CustomerAdapter()
        binding.customerRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = customerAdapter
        }

        lifecycleScope.launch {
            viewModel.customers.collect { customers ->
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
fun WalletScreenContent(
    balance: String,
    onSearchCustomer: (String) -> Unit,
    onSaveBalance: () -> Unit
) {
    // Your composable content here
}
You don't need to create an adapter and data