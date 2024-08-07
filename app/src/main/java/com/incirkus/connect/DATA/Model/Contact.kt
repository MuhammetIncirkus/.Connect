package com.incirkus.connect.DATA.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_table")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String,
    var image: Int,
    var email: String,
    var phoneNumber: String,
    var password: String,
)