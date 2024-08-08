package com.incirkus.connect.DATA

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.incirkus.connect.DATA.Model.Message
import com.incirkus.connect.DATA.local.ConnectDatabase
import com.incirkus.connect.DATA.local.SampleData

class Repository(private val db: ConnectDatabase) {

    val currentUserID : Long = 1

    val contactList = db.contactDao().getAllContacts(currentUserID)
    val loggedUser = db.contactDao().getloggedInUser(currentUserID)
    private var _usersChatsMessageList = MutableLiveData<List<Message>>()
    val usersChatMessageList: LiveData<List<Message>> = _usersChatsMessageList




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

    suspend fun getcurrentChatMessageList(){
        _usersChatsMessageList.value = db.messageDao().getUsersChatMessageList(currentUserID).value
    }

}