package com.alkewallet.auth

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.alkewallet.R
import com.alkewallet.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCrearCuenta.setOnClickListener {
            navigateTo(SignupFragment())
        }

        binding.tvYaTieneCuenta.setOnClickListener {
            navigateTo(LoginFragment())
        }
    }

    fun navigateTo(fragment: Fragment) {
        binding.groupSelector.visibility = View.GONE
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(binding.fragmentContainerView.id, fragment)
        }
    }
}