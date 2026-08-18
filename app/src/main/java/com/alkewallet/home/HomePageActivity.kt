package com.alkewallet.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alkewallet.databinding.ActivityHomeBinding
import com.alkewallet.profile.ProfileActivity
import com.alkewallet.transactions.RequestMoneyActivity
import com.alkewallet.transactions.SendMoneyActivity

class HomePageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transactions = listOf(
            Transaction("Yara Khalil", "17 Aug 2026", 15.00, true),
            Transaction("Sara Ibrahim", "16 Aug 2026", 20.50, false),
            Transaction("Ahmad Ibrahim", "15 Aug 2026", 12.40, false),
            Transaction("Reem Khaled", "14 Aug 2026", 21.30, true),
            Transaction("Hiba Saleh", "13 Aug 2026", 9.00, false)
        )

        renderTransactions(transactions)

        binding.ivProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.btnEnviarDinero.setOnClickListener {
            startActivity(Intent(this, SendMoneyActivity::class.java))
        }

        binding.btnIngresarDinero.setOnClickListener {
            startActivity(Intent(this, RequestMoneyActivity::class.java))
        }
    }

    private fun renderTransactions(list: List<Transaction>) {
        if (list.isEmpty()) {
            binding.rvTransactions.visibility = View.GONE
            binding.emptyStateContainer.visibility = View.VISIBLE
        } else {
            binding.rvTransactions.visibility = View.VISIBLE
            binding.emptyStateContainer.visibility = View.GONE
            binding.rvTransactions.layoutManager = LinearLayoutManager(this)
            binding.rvTransactions.adapter = TransactionAdapter(list)
        }
    }
}