package com.incirkus.connect.DATA.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey(autoGenerate = true)
    val messageId: Long = 0,
    val chatRoomId: Long,
    val senderId: Long,
    val messageText: String,
    val timestamp: Long,
    var messageStatus: String,
//    val fileUrl: String,
//    val fileType: String,
//    val fileName: String,
)
