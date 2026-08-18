package com.alkewallet.profile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alkewallet.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivEdit.setOnClickListener {
            Toast.makeText(this, "Función no disponible", Toast.LENGTH_SHORT).show()
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}