package com.alkewallet.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alkewallet.databinding.ActivityHomeBinding
import com.alkewallet.model.AuthModel
import com.alkewallet.model.WalletAccountModel
import com.alkewallet.profile.ProfileActivity
import com.alkewallet.transactions.RequestMoneyActivity
import com.alkewallet.transactions.SendMoneyActivity
import java.util.Locale

class HomePageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWindowInsets()

        binding.ivProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.btnEnviarDinero.setOnClickListener {
            startActivity(Intent(this, SendMoneyActivity::class.java))
        }

        binding.btnIngresarDinero.setOnClickListener {
            startActivity(Intent(this, RequestMoneyActivity::class.java))
        }

        refreshDashboard()
    }

    private fun setupWindowInsets() {
        val initialLeft = binding.root.paddingLeft
        val initialTop = binding.root.paddingTop
        val initialRight = binding.root.paddingRight
        val initialBottom = binding.root.paddingBottom

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.root.setPadding(
                initialLeft + insets.left,
                initialTop + insets.top,
                initialRight + insets.right,
                initialBottom + insets.bottom
            )
            windowInsets
        }
    }

    override fun onResume() {
        super.onResume()
        refreshDashboard()
    }

    private fun refreshDashboard() {
        val currentUser = AuthModel.currentUser
        val displayName = currentUser?.fullName() ?: "Amanda"
        binding.tvGreeting.text = "Hola, $displayName!"

        binding.tvBalanceAmount.text = String.format(Locale.US, "$%.2f", WalletAccountModel.balance)

        val transactions = WalletAccountModel.getTransactions()
        if (transactions.isEmpty()) {
            binding.rvTransactions.visibility = View.GONE
            binding.emptyStateContainer.visibility = View.VISIBLE
        } else {
            binding.rvTransactions.visibility = View.VISIBLE
            binding.emptyStateContainer.visibility = View.GONE
            binding.rvTransactions.layoutManager = LinearLayoutManager(this)
            binding.rvTransactions.adapter = TransactionAdapter(transactions)
        }
    }
}
