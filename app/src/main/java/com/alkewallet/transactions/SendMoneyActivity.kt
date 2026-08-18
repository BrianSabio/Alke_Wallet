package com.alkewallet.transactions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alkewallet.databinding.ActivitySendMoneyBinding

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
            // Simulación de envío
            finish()
        }
    }
}