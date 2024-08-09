package com.incirkus.connect.DATA.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "leave_requests")
data class LeaveRequest(
    @PrimaryKey(autoGenerate = true)
    val requestId: Long = 0,
    val userId: String,
    val startDate: Long,
    val endDate: Long,
    var status: String,

    )