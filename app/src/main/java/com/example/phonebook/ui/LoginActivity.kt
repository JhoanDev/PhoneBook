package com.example.phonebook.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.phonebook.database.DBHelper
import com.example.phonebook.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(this)
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)

        sharedPreferences.getString("username", "")?.takeIf { it.isNotEmpty() }?.let {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.buttonLogin.setOnClickListener {
            val username = binding.editUsername.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()
            val isChecked = binding.checkboxLogged.isChecked

            when {
                username.isEmpty() || password.isEmpty() -> {
                    showToast("Please fill in all fields")
                }

                db.login(username, password) -> {
                    if (isChecked) {
                        sharedPreferences.edit().apply {
                            putString("username", username)
                            apply()
                        }
                    }
                    showToast("Login successful!")
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

                else -> {
                    showToast("Invalid username or password")
                    binding.editPassword.text.clear()
                    binding.editUsername.text.clear()
                }
            }
        }

        binding.textSignUp.setOnClickListener {
            startActivity(Intent(this, SingUpActivity::class.java))
        }

        binding.textRecoverPassword.setOnClickListener {

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

}