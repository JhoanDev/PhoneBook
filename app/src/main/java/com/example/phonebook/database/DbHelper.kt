package com.example.phonebook.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.phonebook.model.UserModel

class DbHelper(context: Context) : SQLiteOpenHelper(context, "database.db", null, 1) {
    private val sql = arrayOf(
        "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)",
        "INSERT INTO users (username, password) VALUES ('admin', 'admin')"
    )

    override fun onCreate(db: SQLiteDatabase?) {
        sql.forEach {
            db?.execSQL(it)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Implementação de atualização do banco de dados, se necessário
    }

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
}
