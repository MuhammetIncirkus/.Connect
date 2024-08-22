package com.incirkus.connect.DATA.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

data class Message(
    val messageId: String = "",
    val chatRoomId: String = "",
    val senderId: String? = "",
    val messageText: String? = "",
    val timestamp: Long? = 0,
    var messageStatus: String? = ""
)
