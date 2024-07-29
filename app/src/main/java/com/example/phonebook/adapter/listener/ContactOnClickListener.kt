package com.example.phonebook.adapter.listener

import com.example.phonebook.model.ContactModel

class ContactOnClickListener(val clickListener: (contact: ContactModel) -> Unit) {
    fun onClick(contact: ContactModel) = clickListener
}