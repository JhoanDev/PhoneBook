package com.example.phonebook.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.phonebook.database.DBHelper
import com.example.phonebook.databinding.ActivityMainBinding
import com.example.phonebook.model.ContactModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contacts: ArrayList<ContactModel>
    private lateinit var adapter: ArrayAdapter<ContactModel>
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val dbHelper = DBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)


        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                when (result.resultCode) {
                    1 -> {
                        contacts = dbHelper.getAllContacts()
                        updateContactList()
                    }

                    0 -> {
                        showToast("Operation canceled")
                    }

                    else -> {
                        showToast("No action taken")
                    }
                }
            }

        binding.buttonLogout.setOnClickListener {
            sharedPreferences.edit().putString("username", "").apply()
            finish()
        }

        contacts = dbHelper.getAllContacts()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contacts)
        binding.listViewContacts.adapter = adapter

        binding.listViewContacts.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, ContactDetailActivity::class.java).apply {
                putExtra("id", contacts[position].id)
            }
            //startActivity(intent)
            resultLauncher.launch(intent)
        }

        binding.buttonAdd.setOnClickListener {
            resultLauncher.launch(Intent(this, NewContactActivity::class.java))
        }
    }

    private fun updateContactList() {
        adapter.clear()
        adapter.addAll(dbHelper.getAllContacts())
        adapter.notifyDataSetChanged()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
