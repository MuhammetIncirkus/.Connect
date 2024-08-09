package com.incirkus.connect.DATA.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_participants")
data class ChatParticipants(
    @PrimaryKey(autoGenerate = true)
    val chatParticipantsId: Long = 0,
    val chatRoomId: Long,
    val user1Id: Long,
    val user2Id: Long,
)