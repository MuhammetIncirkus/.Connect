package com.incirkus.connect.DATA

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.incirkus.connect.DATA.Model.Message
import com.incirkus.connect.DATA.local.ConnectDatabase
import com.incirkus.connect.DATA.local.SampleData

class Repository(private val db: ConnectDatabase) {

    val currentUserID : String = "f086aaa7-8a22-4d67-a1f5-9ca01f309d81"

    val userList = db.userDao().getAllContacts(currentUserID)
//    val loggedUser = db.contactDao().getloggedInUser(currentUserID)
    private var _usersChatsMessageList = MutableLiveData<List<Message>>()
    val usersChatMessageList: LiveData<List<Message>> = _usersChatsMessageList




    suspend fun preload(){
        val userList = SampleData.userList
//        val departmentList = SampleData.departmentList
//        val messageList = SampleData.messageList
//        val chatRoomList = SampleData.chatRoomList

        for (user in userList){
            db.userDao().insert(user)
        }
//        for (message in messageList){
//            db.messageDao().insert(message)
//        }
//        for (department in departmentList){
//            db.departmentDao().insert(department)
//        }
//        for (chatRoom in chatRoomList){
//            db.chatRoomDao().insert(chatRoom)
//        }
//        for (department in departmentList){
//            db.departmentDao().insert(department)
//        }
//        for (department in departmentList){
//            db.departmentDao().insert(department)
//        }
    }

//    suspend fun getcurrentChatMessageList(){
//        _usersChatsMessageList.value = db.messageDao().getUsersChatMessageList(currentUserID).value
//    }

}