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
                    Toast.makeText(
                        applicationContext, "Please fill in all fields", Toast.LENGTH_SHORT
                    ).show()
                }

                password != passwordConfirm -> {
                    Toast.makeText(
                        applicationContext, "Passwords do not match", Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    val result = db.insertUser(username, password)
                    if (result > 0) {
                        Toast.makeText(
                            applicationContext, "Sign up successful!", Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Sign up failed, please try again",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.editUsername.text.clear()
                        binding.editPassword.text.clear()
                        binding.editConfirmPassword.text.clear()
                    }
                }
            }
        }
    }
}