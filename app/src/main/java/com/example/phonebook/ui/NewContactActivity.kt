package com.example.phonebook.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.phonebook.R
import com.example.phonebook.database.DBHelper
import com.example.phonebook.databinding.ActivityNewContactBinding

class NewContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewContactBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private var id: Int? = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val resultIntent = intent
        val db = DBHelper(applicationContext)

        binding.buttonSave.setOnClickListener {
            val name = binding.editName.text.toString().trim()
            val address = binding.editAddress.text.toString().trim()
            val email = binding.editEmail.text.toString().trim()
            val phone = binding.editPhone.text.toString().trim()
            var imageId = -1
            if (id != null){
                imageId = id as Int
            }


            when {
                name.isEmpty() || address.isEmpty() || email.isEmpty() || phone.isEmpty() -> {
                    showToast("Please fill in all fields correctly.")
                }

                else -> {
                    val result = db.insertContact(name, address, email, phone.toIntOrNull() ?: 0, imageId)
                    if (result > 0) {
                        showToast("Contact saved successfully!")
                        setResult(1, resultIntent)
                        finish()
                    } else {
                        showToast("Failed to save contact. Please try again.")
                    }
                }
            }
        }

        binding.buttonCancel.setOnClickListener {
            setResult(0, resultIntent)
            finish()
        }

        binding.imageContact.setOnClickListener {
            launcher.launch(Intent(applicationContext, ContactImageSelectionActivity::class.java))
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == 1 && it.data != null) {
                id = it.data?.extras?.getInt("id") ?: R.drawable.baseline_person_24
                binding.imageContact.setImageResource(id!!)
            } else {
                id = -1
                binding.imageContact.setImageResource(R.drawable.baseline_person_24)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}
