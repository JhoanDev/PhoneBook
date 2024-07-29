package com.example.phonebook.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.phonebook.database.DBHelper
import com.example.phonebook.databinding.ActivityContactDetailBinding
import com.example.phonebook.model.ContactModel

class ContactDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactDetailBinding
    private lateinit var db: DBHelper
    private var contactObj = ContactModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val i = intent
        val id = i.extras?.getInt("id")
        db = DBHelper(applicationContext)

        if (id != null) {
            contactObj = db.getContact(id)!!

            binding.editName.setText(contactObj.name)
            binding.editEmail.setText(contactObj.email)
            binding.editAddress.setText(contactObj.address)
            binding.editPhone.setText(contactObj.phone.toString())
        }

        binding.buttonSave.setOnClickListener {
            val res = db.updateContact(
                id = contactObj.id,
                name = binding.editName.text.toString(),
                address = binding.editAddress.text.toString(),
                email = binding.editEmail.text.toString(),
                phone = binding.editPhone.text.toString().toInt(),
                imageId = contactObj.imageId
            )
            if (res > 0) {
                showToast("Update ok")
                setResult(1, i)
                finish()
            }else{
                showToast("Update error")
                setResult(0, i)
                finish()
            }
        }
        binding.buttonCancel.setOnClickListener {
            binding.editName.setText(contactObj.name)
            binding.editEmail.setText(contactObj.email)
            binding.editAddress.setText(contactObj.address)
            binding.editPhone.setText(contactObj.phone.toString())
        }
        binding.buttonDelete.setOnClickListener {
            val res = db.deleteContact(contactObj.id)
            if (res > 0) {
                showToast("Delete ok")
                setResult(1, i)
                finish()
            }else{
                showToast("Delete error")
                setResult(0, i)
                finish()
            }
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}