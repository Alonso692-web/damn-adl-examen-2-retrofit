package com.example.adl_examen_ii.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user",
    indices = [Index(value = ["username_usuario"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "nombre_usuario")
    val nameUser: String,
    @ColumnInfo(name = "username_usuario")
    val usernameUser: String,
    @ColumnInfo(name = "email_usuario")
    val emailUser: String,
    @ColumnInfo(name = "address_usuario")
    val addressUser: String,
)