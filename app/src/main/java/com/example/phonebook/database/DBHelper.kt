package com.example.phonebook.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.phonebook.model.ContactModel
import com.example.phonebook.model.UserModel

class DBHelper(context: Context) : SQLiteOpenHelper(context, "database.db", null, 1) {
    private val sql = arrayOf(
        "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT)",
        "INSERT INTO users (username, password) VALUES ('admin', 'admin')",
        "CREATE TABLE contacts (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE, address TEXT, email TEXT UNIQUE, phone INTEGER UNIQUE, imageId INTEGER)",
        "INSERT INTO contacts (name, address, email, phone, imageId) VALUES ('John Doe', '123 Main St', 'john@example.com', 1234567890, 1)",
        "INSERT INTO contacts (name, address, email, phone, imageId) VALUES ('Jane Smith', '456 Elm St', 'jane@example.com', 9876543210, 2)"
    )

    override fun onCreate(db: SQLiteDatabase?) {
        sql.forEach {
            db?.execSQL(it)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Implementação de atualização do banco de dados, se necessário
    }

    // Funções CRUD para a tabela "users"
    fun insertUser(username: String, password: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("username", username)
            put("password", password)
        }
        val res = db.insert("users", null, contentValues)
        db.close()
        return res
    }

    fun updateUser(id: Int, username: String, password: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("username", username)
            put("password", password)
        }
        val res = db.update("users", contentValues, "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun deleteUser(id: Int): Int {
        val db = this.writableDatabase
        val res = db.delete("users", "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun getUser(username: String, password: String): UserModel? {
        val db = this.readableDatabase
        val user = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", arrayOf(username, password))
        var userObj: UserModel? = null

        if (user.moveToFirst()) {
            val indexId = user.getColumnIndex("id")
            val indexUsername = user.getColumnIndex("username")
            val indexPassword = user.getColumnIndex("password")

            if (indexId != -1 && indexUsername != -1 && indexPassword != -1) {
                userObj = UserModel(
                    id = user.getInt(indexId),
                    username = user.getString(indexUsername),
                    password = user.getString(indexPassword)
                )
            }
        }
        user.close()
        db.close()
        return userObj
    }

    fun login(username: String, password: String): Boolean {
        return getUser(username, password) != null
    }

    // Funções CRUD para a tabela "contacts"
    fun insertContact(name: String, address: String, email: String, phone: Int, imageId: Int): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("name", name)
            put("address", address)
            put("email", email)
            put("phone", phone)
            put("imageId", imageId)
        }
        val res = db.insert("contacts", null, contentValues)
        db.close()
        return res
    }

    fun updateContact(id: Int, name: String, address: String, email: String, phone: Int, imageId: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("name", name)
            put("address", address)
            put("email", email)
            put("phone", phone)
            put("imageId", imageId)
        }
        val res = db.update("contacts", contentValues, "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun deleteContact(id: Int): Int {
        val db = this.writableDatabase
        val res = db.delete("contacts", "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun getContact(id: Int): ContactModel? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM contacts WHERE id=?", arrayOf(id.toString()))
        var contact: ContactModel? = null

        if (cursor.moveToFirst()) {
            val indexId = cursor.getColumnIndex("id")
            val indexName = cursor.getColumnIndex("name")
            val indexAddress = cursor.getColumnIndex("address")
            val indexEmail = cursor.getColumnIndex("email")
            val indexPhone = cursor.getColumnIndex("phone")
            val indexImageId = cursor.getColumnIndex("imageId")

            if (indexId != -1 && indexName != -1 && indexAddress != -1 && indexEmail != -1 && indexPhone != -1 && indexImageId != -1) {
                contact = ContactModel(
                    id = cursor.getInt(indexId),
                    name = cursor.getString(indexName),
                    address = cursor.getString(indexAddress),
                    email = cursor.getString(indexEmail),
                    phone = cursor.getInt(indexPhone),
                    imageId = cursor.getInt(indexImageId)
                )
            }
        }
        cursor.close()
        db.close()
        return contact
    }

    fun getAllContacts(): ArrayList<ContactModel> {
        val contactList = ArrayList<ContactModel>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM contacts", null)

        if (cursor.moveToFirst()) {
            do {
                val indexId = cursor.getColumnIndex("id")
                val indexName = cursor.getColumnIndex("name")
                val indexAddress = cursor.getColumnIndex("address")
                val indexEmail = cursor.getColumnIndex("email")
                val indexPhone = cursor.getColumnIndex("phone")
                val indexImageId = cursor.getColumnIndex("imageId")

                if (indexId != -1 && indexName != -1 && indexAddress != -1 && indexEmail != -1 && indexPhone != -1 && indexImageId != -1) {
                    contactList.add(ContactModel(
                        id = cursor.getInt(indexId),
                        name = cursor.getString(indexName),
                        address = cursor.getString(indexAddress),
                        email = cursor.getString(indexEmail),
                        phone = cursor.getInt(indexPhone),
                        imageId = cursor.getInt(indexImageId)
                    ))
                }
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return contactList
    }

}
