package com.incirkus.connect.DATA.Model

data class Message(
    val messageId: String = "",
    val chatRoomId: String? = "",
    val senderId: String? = "",
    val messageText: String? = "",
    val timestamp: Long? = 0,
    var messageStatus: String? = "",

)
