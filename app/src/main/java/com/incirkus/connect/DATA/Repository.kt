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
import com.incirkus.connect.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val db: ConnectDatabase, private val viewModel: ViewModel) {

    private val currentUserID: Long = 1

    private var _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> = _currentUser

    private var _userList = MutableLiveData<List<User>>()
    val userList = db.userDao().getAllContacts(currentUserID)

    private var _currentChatRoom = MutableLiveData<ChatRoom>()
    val currentChatRoom : LiveData<ChatRoom> = _currentChatRoom

    private var _currentChatParticipants = MutableLiveData<ChatParticipants>()
    val currentChatParticipants : LiveData<ChatParticipants> = _currentChatParticipants

    private var _usersChatRoomIdList = MutableLiveData<List<Long>>()
    val usersChatRoomIdList : LiveData<List<Long>> = _usersChatRoomIdList

    private var _usersChatRoomList = MutableLiveData<List<ChatRoom>>()
    val usersChatRoomList : LiveData<List<ChatRoom>> = _usersChatRoomList

    suspend fun insertCurrentUser(){
        withContext(Dispatchers.IO) {
            if (_currentUser.value != null){

                db.currentUserDao().insertUser(_currentUser.value!!)
            }

        }
    }

    suspend fun loadCurrentUser(){
        withContext(Dispatchers.IO) {
            _currentUser.postValue(db.userDao().getloggedInUser(currentUserID))
            insertCurrentUser()
        }
    }

    suspend fun loadUserList(){
        withContext(Dispatchers.IO) {
            _userList.postValue(db.userDao().getAllContacts(currentUserID).value)
        }
    }

    suspend fun preload() {
        withContext(Dispatchers.IO) {
            val userlist = SampleData.users
            try {
                for (user in userlist) {
                    db.userDao().insert(user)
                    Log.e("preload", "geklappt")
                }
            } catch (e: Exception) {
                Log.e("preload", e.message.toString())
            }
        }
    }

    suspend fun createNewChatroom(currentUser: User, secondUser: User) {
        withContext(Dispatchers.IO) {
            val chatRoomName = "${currentUser.firstName.replace(" ","").replace("-","")}${secondUser.firstName.replace(" ","").replace("-","")}"
            var chatRoomID: Long = 0L
            var newChatRoom = ChatRoom(
                chatRoomName = chatRoomName,
                lastMessage = "Hi",
                lastActivityTimestamp = System.currentTimeMillis(),
                chatParticipantsId = 0L
            )
            try {
                chatRoomID = db.chatRoomDao().insert(newChatRoom)
                Log.e("newChatRoom", "geklappt")
            } catch (e: Exception) {
                Log.e("newChatRoom", e.message.toString())
            }

            createNewChatParticipants(chatRoomID, currentUser, secondUser, newChatRoom)
            getChatRoom(chatRoomID)
        }
    }

    suspend fun createNewChatParticipants(chatRoomID: Long, currentUser: User, secondUser: User, newChatRoom: ChatRoom){
        withContext(Dispatchers.IO) {
            val newChatParticipants = ChatParticipants(
                chatRoomId = chatRoomID,
                user1Id = currentUser.userId,
                user2Id = secondUser.userId
            )
            var chatParticipantsId: Long = 0L
            try {
                chatParticipantsId = db.chatParticipantsDao().insert(newChatParticipants)
                Log.e("newChatParticipants", "geklappt")
            } catch (e: Exception) {
                Log.e("newChatParticipants", e.message.toString())
            }
            updateChatroom(chatParticipantsId, newChatRoom)
            getChatParticipants(chatParticipantsId)
        }
    }

    suspend fun updateChatroom(chatParticipantsId: Long, newChatRoom: ChatRoom){
        withContext(Dispatchers.IO) {
            newChatRoom.chatParticipantsId = chatParticipantsId
            try {
                db.chatRoomDao().update(newChatRoom)
                Log.e("updateChatRoom", "geklappt")
            } catch (e: Exception) {
                Log.e("updateChatRoom", e.message.toString())
            }
        }
    }

    suspend fun newUser(user:User){
        withContext(Dispatchers.IO) {
            try {
                db.userDao().insert(user)
                Log.e("newUser", "geklappt")
            } catch (e: Exception) {
                Log.e("newUser", e.message.toString())
            }
        }
    }

    suspend fun createNewChatroom2(chatRoom: ChatRoom) {
        withContext(Dispatchers.IO) {
            try {
                db.chatRoomDao().insert(chatRoom)
                Log.e("newChatRoom", "geklappt")
            } catch (e: Exception) {
                Log.e("newChatRoom", e.message.toString())
            }

        }
    }

    suspend fun getChatRoom(chatRoomId:Long){
        withContext(Dispatchers.IO) {
            try {
                _currentChatRoom.postValue(db.chatRoomDao().getChatRoomWithID(chatRoomId))
                Log.e("ChatRoom", "Laden hat geklappt")
            }catch (e:Exception){
                Log.e("ChatRoom", e.message.toString())
            }
        }
    }

    suspend fun getChatParticipants(chatParticipantsId:Long){
        withContext(Dispatchers.IO) {
            try {
                _currentChatParticipants.postValue(db.chatParticipantsDao().getChatParticipantsWithChatRoomId(chatParticipantsId))
                Log.e("ChatParticipants", "Laden hat geklappt")
            }catch (e:Exception){
                Log.e("ChatParticipants", e.message.toString())
            }
        }
    }

    suspend fun sendMessage(message: Message){
        withContext(Dispatchers.IO) {
            try {
                db.messageDao().insert(message)
                Log.e("sendMessage", "Nachricht verschickt")
            }catch (e:Exception){
                Log.e("sendMessage", e.message.toString())
            }
            updateChatRoomMessage(message)
        }
    }

    suspend fun updateChatRoomMessage(message: Message){
        withContext(Dispatchers.IO) {
            try {
                var updatedChatRoom: ChatRoom = _currentChatRoom.value!!
                updatedChatRoom.lastMessage = message.messageText
                db.chatRoomDao().update(updatedChatRoom)
                Log.e("sendMessage", "Nachricht verschickt")
            }catch (e:Exception){
                Log.e("sendMessage", e.message.toString())
            }
        }
    }

    suspend fun loadUsersChatParticipantsLists(){
        withContext(Dispatchers.IO) {
            try {
                _usersChatRoomIdList.postValue(db.chatParticipantsDao().loadUsersChatLists(currentUserID))
                Log.e("usersChatList", "wurde geladen")
            }catch (e:Exception){
                Log.e("usersChatList", e.message.toString())
            }
        }
        loadChatRoomList()
    }

    suspend fun loadChatRoomList(){
        var chatRoomList: MutableList<ChatRoom> = mutableListOf()
        withContext(Dispatchers.IO) {
            try {
                if (!_usersChatRoomIdList.value.isNullOrEmpty()){
                    for (id in _usersChatRoomIdList.value!!){
                        chatRoomList.add(db.chatRoomDao().getChatRoomWithID(id))
                    }
                }
                _usersChatRoomList.postValue(chatRoomList)
                Log.e("chatRoomList", "wurde geladen")
            }catch (e:Exception){
                Log.e("chatRoomList", e.message.toString())
            }
        }
    }

    suspend fun getOneUserById(userID: Long): LiveData<User>{
        lateinit var user : LiveData<User>
        withContext(Dispatchers.IO) {
            try {
                user = db.userDao().getOneUser(userID)
                Log.e("chatPartner", "wurde geladen")
            }catch (e:Exception){
                Log.e("sendMessage", e.message.toString())
            }
        }
        return user
    }

}