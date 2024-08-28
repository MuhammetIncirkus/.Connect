package com.incirkus.connect.DATA.Model

import androidx.room.Entity
import androidx.room.PrimaryKey


data class Attachment(

    var attachmentId: String = "",
    var senderID: String? = "",
    var attachmentName: String? = "",
    var attachmentType: String? = "",
    var chatRoomId: String? = "",
    var timestamp: Long? = 0,
    var path: String? = "",
)
