package com.example.phonebook.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.phonebook.database.DBHelper
import com.example.phonebook.databinding.ActivityMainBinding
import com.example.phonebook.model.ContactModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contacts: ArrayList<ContactModel>
    private lateinit var adapter: ArrayAdapter<ContactModel>
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbHelper = DBHelper(this)
        sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE)

        binding.buttonLogout.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("username", "")
            editor.apply()
            finish()
        }

        contacts = dbHelper.getAllContacts()

        adapter =
            ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, contacts)

        binding.listViewContacts.adapter = adapter

        binding.listViewContacts.setOnItemClickListener { _, _, i, l ->
            val intent = Intent(applicationContext, ContactDetailActivity::class.java)
            intent.putExtra("id", contacts[i].id)
            startActivity(intent)
        }

        binding.buttonAdd.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    NewContactActivity::class.java
                )
            )
        }

    }
}