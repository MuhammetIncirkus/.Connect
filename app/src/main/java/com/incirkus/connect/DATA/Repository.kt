package com.incirkus.connect.DATA

import com.incirkus.connect.DATA.local.ConnectDatabase
import com.incirkus.connect.DATA.local.SampleData

class Repository(private val db: ConnectDatabase) {

    suspend fun preload(){
        val contactList = SampleData.sampleContacts
        val messageList = SampleData.sampleMessages
        for (contact in contactList){
            db.contactDao().insert(contact)
        }
        for (message in messageList){
            db.messageDao().insert(message)
        }
    }

}