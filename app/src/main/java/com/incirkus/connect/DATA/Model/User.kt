package com.incirkus.connect.DATA.Model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Long = 0,
    val firstName: String,
    var lastName: String,
    val fullName: String,
    var image: Int,
    var email: String,
    var phoneNumber: String,
    var password: String,
    var departmentId: String,
)