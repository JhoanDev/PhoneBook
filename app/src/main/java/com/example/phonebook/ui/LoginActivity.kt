package com.example.phonebook.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.phonebook.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.textSignUp.setOnClickListener{
            startActivity(Intent(this, SingUpActivity::class.java))
        }
        binding.textRecoverPassword.setOnClickListener{}
    }
}