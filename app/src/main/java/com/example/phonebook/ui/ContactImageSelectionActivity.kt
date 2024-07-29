package com.example.phonebook.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.phonebook.R
import com.example.phonebook.databinding.ActivityContactImageSelectionBinding

class ContactImageSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactImageSelectionBinding
    private lateinit var i: Intent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactImageSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        i = intent

        binding.imageMan.setOnClickListener { sendID(R.drawable.baseline_man_24) }
        binding.imageWoman.setOnClickListener { sendID(R.drawable.baseline_woman_24) }
        binding.buttonRemoveImage.setOnClickListener{sendID(R.drawable.baseline_person_24)}
    }

    private fun sendID(id: Int) {
        i.putExtra("id", id)
        setResult(1, i)
        finish()
    }
}