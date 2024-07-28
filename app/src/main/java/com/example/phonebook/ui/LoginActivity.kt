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

        sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE)

        val usernameT = sharedPreferences.getString("username", "")
        if (!usernameT.isNullOrEmpty()) {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.buttonLogin.setOnClickListener {
            val username = binding.editUsername.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()
            val logged = binding.checkboxLogged.isChecked

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val loginSuccess = db.login(username, password)
                if (loginSuccess) {
                    if (logged){
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("username", username)
                        editor.apply()
                    }
                    Toast.makeText(
                        applicationContext, "Login successful!", Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(
                        applicationContext, "Invalid username or password", Toast.LENGTH_SHORT
                    ).show()
                    binding.editPassword.text.clear()
                    binding.editUsername.text.clear()
                }
            } else {
                Toast.makeText(
                    applicationContext, "Please fill in all fields", Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.textSignUp.setOnClickListener {
            startActivity(Intent(this, SingUpActivity::class.java))
        }
        binding.textRecoverPassword.setOnClickListener {}
    }
}