package com.example.basicwallet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basicwallet.R
import com.example.basicwallet.model.Customer

class CustomerAdapter : RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {

    private val customers = mutableListOf<Customer>()

    fun submitList(customers: List<Customer>) {
        this.customers.clear()
        this.customers.addAll(customers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_customer, parent, false)
        return CustomerViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.bind(customers[position])
    }

    override fun getItemCount(): Int = customers.size

    inner class CustomerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(customer: Customer) {
            // Bind customer data to the views in item_customer.xml
            // For example:
            val customerName = itemView.findViewById<TextView>(R.id.customer_name)
            val customerPhone = itemView.findViewById<TextView>(R.id.customer_phone)
            customerName.text = customer.name
            customerPhone.text = customer.phone
        }
    }
}