package com.quandoo.androidtask.ui.customers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quandoo.androidtask.databinding.CustomerCellBinding
import com.quandoo.androidtask.domain.model.Customer
import com.quandoo.androidtask.ui.base.BaseViewHolder
import com.quandoo.androidtask.utils.Logger
import com.squareup.picasso.Picasso

 class CustomersRvAdapter(private val customers: List<Customer>,
                                  private val customerClickListener: (Customer) -> Unit) :
        RecyclerView.Adapter<CustomersRvAdapter.CustomerViewHolder>(), Logger {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CustomerViewHolder {
        return CustomerViewHolder(
            CustomerCellBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            ),customerClickListener)
    }

    override fun onBindViewHolder(viewHolder: CustomerViewHolder, i: Int) {
        val customer = this.customers[i]
        viewHolder.onBind(customer)
    }

    override fun getItemCount(): Int {
        return customers.size
    }

    class CustomerViewHolder(private val binding: CustomerCellBinding,private val customerClickListener: (Customer) -> Unit) : BaseViewHolder<Customer>(binding.root) {
        override fun onBind(customer: Customer) {
            binding.customerNameTextView.text = customer.firstName + " " + customer.lastName
            Picasso.get().load(customer.imageUrl).into(binding.customerAvatarImageView)
            binding.root.setOnClickListener { v ->
                customerClickListener(customer)
            }
        }
    }
}