package com.alkewallet.transactions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alkewallet.databinding.ActivityRequestMoneyBinding

class RequestMoneyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRequestMoneyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestMoneyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnIngresarDinero.setOnClickListener {
            // Simulación de ingreso
            finish()
        }
    }
}