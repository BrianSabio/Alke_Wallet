package com.alkewallet.transactions

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alkewallet.databinding.ActivitySendMoneyBinding
import com.alkewallet.model.WalletAccountModel
import com.alkewallet.model.WalletResult
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SendMoneyActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySendMoneyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendMoneyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnEnviarDinero.setOnClickListener {
            val recipient = binding.etRecipient.text?.toString()?.trim().orEmpty()
            if (recipient.isEmpty()) {
                Toast.makeText(this, "Ingrese un destinatario", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amountString = binding.etAmount.text?.toString()?.trim().orEmpty()
            val amount = amountString.toDoubleOrNull()
            if (amount == null) {
                Toast.makeText(this, "Ingrese un monto válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val notes = binding.etNotes.text?.toString()?.trim().orEmpty()
            val targetName = if (notes.isNotEmpty()) {
                "$recipient — $notes"
            } else {
                recipient
            }

            val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
            when (val result = WalletAccountModel.sendMoney(targetName, amount, date)) {
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
}
