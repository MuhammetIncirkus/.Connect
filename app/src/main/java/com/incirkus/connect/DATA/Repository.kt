package com.incirkus.connect.DATA

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.incirkus.connect.DATA.Model.ChatParticipants
import com.incirkus.connect.DATA.Model.ChatRoom
import com.incirkus.connect.DATA.Model.Message
import com.incirkus.connect.DATA.Model.User
import com.incirkus.connect.DATA.local.ConnectDatabase
import com.incirkus.connect.DATA.local.SampleData

class Repository(private val db: ConnectDatabase) {

    /*val currentUserID : String = "f086aaa7-8a22-4d67-a1f5-9ca01f309d81"

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
//    }*/

    private val currentUserID: Long = 1
    var _currentUser = MutableLiveData<User>()
    val currentUser = _currentUser
    var _userList = MutableLiveData<List<User>>()
    val userList = db.userDao().getAllContacts(currentUserID)


    suspend fun loadCurrentUser(){
        _currentUser.postValue(db.userDao().getloggedInUser(currentUserID).value)
    }
    suspend fun loadUserList(){
        _userList.postValue(db.userDao().getAllContacts(currentUserID).value)
    }


    suspend fun preload() {
        val userlist = SampleData.users
        try {
            for (user in userlist) {
                db.userDao().insert(user)
            }
        } catch (e: Exception) {
            Log.e("preload", e.message.toString())
        }
    }

    suspend fun createNewChatroom(currentUser: User, secondUser: User) {
        val chatRoomName = "${currentUser.firstName}${secondUser.firstName}"
        var newChatRoom = ChatRoom(chatRoomName= chatRoomName, lastMessage = "", lastActivityTimestamp = 0, chatParticipantsId = 0)
        try {
            db.chatRoomDao().insert(newChatRoom)
            val currentChatRoom = db.chatRoomDao().getChatRoomWithName(chatRoomName)
            val newChatParticipants = ChatParticipants(chatRoomId= currentChatRoom.value!!.chatRoomId, user1Id= currentUser.userId, user2Id=secondUser.userId )
            db.chatParticipantsDao().insert(newChatParticipants)
            val currentChatParticipants = db.chatParticipantsDao().getChatParticipantsWithChatRoomId(currentChatRoom.value!!.chatRoomId)
            newChatRoom.chatParticipantsId = currentChatParticipants.value!!.chatParticipantsId
            db.chatRoomDao().update(newChatRoom)
        } catch (e: Exception) {
            Log.e("newChatRoom", e.message.toString())
        }
    }






}