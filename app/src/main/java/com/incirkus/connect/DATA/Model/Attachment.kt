package com.incirkus.connect.DATA.Model

data class Attachment(
    val id: Int,
    val senderID: Int,
    val recipientID: Int,
    val attachmentName: String,
    val attachmentType: String,
    val dispatchDate: String,
    val path: String,
)
