package com.incirkus.connect.DATA.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attachment_table")
data class Attachment(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val senderID: Long,
    val recipientID: Long,
    val attachmentName: String,
    val attachmentType: String,
    val dispatchDate: String,
    val path: String,
)
