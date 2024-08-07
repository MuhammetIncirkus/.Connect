package com.incirkus.connect.DATA.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages_table")
data class Message(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val senderID: Long,
    val recipientID: Long,
    val message: String,
    var isRead: Boolean,
    var dispatchDate: String,
    val receiptDate: String,
)
