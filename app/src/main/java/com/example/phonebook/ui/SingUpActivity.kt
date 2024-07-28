package com.example.phonebook.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.phonebook.database.DBHelper
import com.example.phonebook.databinding.ActivitySingUpBinding

class SingUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(this)

        binding.buttonSignup.setOnClickListener {
            val username = binding.editUsername.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()
            val passwordConfirm = binding.editConfirmPassword.text.toString().trim()

            when {
                username.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty() -> {
                    showToast("Please fill in all fields")
                }

                password != passwordConfirm -> {
                    showToast("Passwords do not match")
                }

                else -> {
                    val result = db.insertUser(username, password)
                    if (result > 0) {
                        showToast("Sign up successful!")
                        finish()
                    } else {
                        showToast("Sign up failed, please try again")
                        clearFields()
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun clearFields() {
        binding.editUsername.text.clear()
        binding.editPassword.text.clear()
        binding.editConfirmPassword.text.clear()
    }

}