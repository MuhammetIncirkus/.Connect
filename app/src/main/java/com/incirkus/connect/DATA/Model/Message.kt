package com.incirkus.connect.DATA.Model

data class Message(
    val id: Int,
    val senderID: Int,
    val recipientID: Int,
    val message: String,
    var isRead: Boolean,
    var dispatchDate: String,
    val receiptDate: String,
)
