package com.incirkus.connect.DATA.Model

data class ChatRoom(

    var chatRoomId: String= "",
    val chatRoomName: String?= "",
    var lastMessage: String?= "",
    var lastMessageSenderId: String? = "",
    var lastActivityTimestamp: Long?= 0,
    var chatParticipants: MutableList<String?> = mutableListOf(),
)