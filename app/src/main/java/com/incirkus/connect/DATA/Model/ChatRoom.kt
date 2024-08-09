package com.incirkus.connect.DATA.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "chat_rooms")
data class ChatRoom(
    @PrimaryKey(autoGenerate = true)
    val chatRoomId: Long = 0,
    val chatRoomName: String,
    val lastMessage: String,
    var lastActivityTimestamp: Long,
    val chatParticipantsId: Long,
    )