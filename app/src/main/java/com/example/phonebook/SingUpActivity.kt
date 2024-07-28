package com.example.phonebook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.phonebook.databinding.ActivitySingUpBinding

class SingUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSignup.setOnClickListener{
            finish()
        }
    }
}