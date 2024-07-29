package com.example.phonebook.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.phonebook.adapter.listener.ContactOnClickListener
import com.example.phonebook.adapter.viewholder.ContactViewHolder
import com.example.phonebook.model.ContactModel

class ContactListAdapter(
    private val contactList: List<ContactModel>,
    private val contactOnClickListener: ContactOnClickListener
) : RecyclerView.Adapter<ContactViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}