package com.alkewallet.transactions

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alkewallet.databinding.ActivityRequestMoneyBinding
import com.alkewallet.model.WalletAccountModel
import com.alkewallet.model.WalletResult
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RequestMoneyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRequestMoneyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestMoneyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWindowInsets()

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnIngresarDinero.setOnClickListener {
            val sender = binding.etRequesterName.text?.toString()?.trim().orEmpty()
            if (sender.isEmpty()) {
                Toast.makeText(this, "Ingrese el nombre del solicitante", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amountString = binding.etAmount.text?.toString()?.trim().orEmpty()
            val amount = amountString.toDoubleOrNull()
            if (amount == null) {
                Toast.makeText(this, "Ingrese un monto válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
            when (val result = WalletAccountModel.requestMoney(sender, amount, date)) {
                is WalletResult.Success -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                    finish()
                }
                is WalletResult.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
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
}
