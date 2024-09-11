package com.incirkus.connect.DATA.Model

data class Attachment(

    var attachmentId: String = "",
    var senderID: String? = "",
    var attachmentName: String? = "",
    var attachmentType: String? = "",
    var chatRoomId: String? = "",
    var timestamp: Long? = 0,
    var path: String? = "",
)
