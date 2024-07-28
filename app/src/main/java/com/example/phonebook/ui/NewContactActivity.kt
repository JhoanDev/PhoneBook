package com.example.phonebook.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.phonebook.database.DBHelper
import com.example.phonebook.databinding.ActivityNewContactBinding

class NewContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewContactBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(applicationContext)
        binding.buttonSave.setOnClickListener {
            val name = binding.editName.text.toString().trim()
            val address = binding.editAddress.text.toString().trim()
            val email = binding.editEmail.text.toString().trim()
            val phone = binding.editPhone.text.toString().trim()
            val imageId = 1

            if (name.isNotEmpty() && address.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()) {
                val result = db.insertContact(name, address, email, phone.toInt(), imageId)
                if (result > 0) {
                    Toast.makeText(applicationContext, "Contact saved successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Failed to save contact. Please try again.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, "Please fill in all fields correctly.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonCancel.setOnClickListener{
            finish()
        }
    }
}