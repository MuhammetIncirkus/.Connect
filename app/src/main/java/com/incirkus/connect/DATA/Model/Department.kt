package com.incirkus.connect.DATA.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "department")
data class Department(
    @PrimaryKey(autoGenerate = true)
    val departmentId: Long = 0,
    val departmentName: String,

    )