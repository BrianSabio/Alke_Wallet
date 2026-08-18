package com.alkewallet.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alkewallet.R
import com.alkewallet.databinding.ItemTransactionBinding

class TransactionAdapter(private val transactions: List<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = transactions[position]
        with(holder.binding) {
            tvUserName.text = item.userName
            tvDate.text = item.date
            tvAmount.text = item.formattedAmount()

            val colorRes = if (item.isSent) {
                ivTransactionIcon.setImageResource(R.drawable.ic_send)
                android.R.color.holo_red_light
            } else {
                ivTransactionIcon.setImageResource(R.drawable.ic_request)
                R.color.alke_green_action
            }

            tvAmount.setTextColor(ContextCompat.getColor(root.context, colorRes))
        }
    }

    override fun getItemCount(): Int = transactions.size
}