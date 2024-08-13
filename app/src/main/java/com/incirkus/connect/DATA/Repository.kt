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
    val currentUser: MutableLiveData<User> = _currentUser


    private var _userList = MutableLiveData<List<User>>()
    //var userList:LiveData<List<User>> = _userList
    val userList = db.userDao().getAllContacts(currentUserID)

    private var _currentChatRoom = MutableLiveData<ChatRoom>()
    val currentChatRoom : LiveData<ChatRoom> = _currentChatRoom

    private var _currentChatParticipants = MutableLiveData<ChatParticipants>()
    val currentChatParticipants : LiveData<ChatParticipants> = _currentChatParticipants

    private var _usersChatRoomIdList = MutableLiveData<List<Long>>()
    val usersChatRoomIdList : LiveData<List<Long>> = _usersChatRoomIdList

    private var _usersChatRoomList = MutableLiveData<List<ChatRoom>>()
    val usersChatRoomList : LiveData<List<ChatRoom>> = _usersChatRoomList

    suspend fun insertCurrentUser(user: User){
        withContext(Dispatchers.IO) {
                try {
                db.currentUserDao().insertUser(user)
                Log.e("ConnectTag", "insertCurrentUser wurde gespeichert")
                }catch (e:Exception){
                    Log.e("ConnectTag", "insertCurrentUser ${e.message.toString()}")
                }
        }
    }

    suspend fun loadCurrentUserFromUserList(){
        lateinit var user: User
        withContext(Dispatchers.IO) {
            try {
                user = db.userDao().getloggedInUser(currentUserID)
                Log.e("ConnectTag", "loadCurrentUserFromUserList wurde geladen")
            }catch (e:Exception){
                Log.e("ConnectTag", "loadCurrentUserFromUserList ${e.message.toString()}")
            }
        }
        //_currentUser.postValue(user)
            insertCurrentUser(user)

    }

    suspend fun loadCurrentUser(){
        withContext(Dispatchers.IO) {
            try {
                _currentUser.postValue(db.currentUserDao().getCurrentUserDatatypeCurrentUser().currentUserToUser())
                Log.e("ConnectTag", "loadCurrentUser wurde geladen")
            }catch (e:Exception){
                Log.e("ConnectTag", "loadCurrentUser ${e.message.toString()}")
            }
        }
    }

//    suspend fun loadUserList(){
//        lateinit var userList2: LiveData<List<User>>
//        withContext(Dispatchers.IO) {
//            try {
//                userList2 = db.userDao().getAllContacts(currentUserID)
//                Log.e("ConnectTag", "loadUserList gefüllt")
//            }catch (e:Exception){
//                Log.e("ConnectTag", "loadUserList ${e.message.toString()}")
//            }
//        }
//        userList = userList2
//    }

    suspend fun preload() {
        withContext(Dispatchers.IO) {
            val userlist = SampleData.users
            try {
                for (user in userlist) {
                    db.userDao().insert(user)
                    Log.e("ConnectTag", "preload geklappt")
                }
            } catch (e: Exception) {
                Log.e("ConnectTag", "preload ${e.message.toString()}")
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
                Log.e("ConnectTag", "createNewChatroom geklappt")
            } catch (e: Exception) {
                Log.e("ConnectTag", "createNewChatroom ${e.message.toString()}")
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
                Log.e("ConnectTag", "geklappt")
            } catch (e: Exception) {
                Log.e("ConnectTag", "createNewChatParticipants ${e.message.toString()}")
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
                Log.e("Connect", "updateChatroom geklappt")
            } catch (e: Exception) {
                Log.e("Connect", "updateChatroom ${e.message.toString()}")
            }
        }
    }

//    suspend fun newUser(user:User){
//        withContext(Dispatchers.IO) {
//            try {
//                db.userDao().insert(user)
//                Log.e("ConnectTag", "newUser geklappt")
//            } catch (e: Exception) {
//                Log.e("ConnectTag", "newUser ${e.message.toString()}")
//            }
//        }
//    }

//    suspend fun createNewChatroom2(chatRoom: ChatRoom) {
//        withContext(Dispatchers.IO) {
//            try {
//                db.chatRoomDao().insert(chatRoom)
//                Log.e("ConnectTag", "createNewChatroom2 geklappt")
//            } catch (e: Exception) {
//                Log.e("ConnectTag", "createNewChatroom2 ${e.message.toString()}")
//            }
//
//        }
//    }

    suspend fun getChatRoom(chatRoomId:Long): ChatRoom{
        lateinit var chatRoom: ChatRoom
        withContext(Dispatchers.IO) {
            try {
                chatRoom = db.chatRoomDao().getChatRoomWithID(chatRoomId)
                Log.e("ConnectTag", "getChatRoom Laden hat geklappt")
            }catch (e:Exception){
                Log.e("ConnectTag", "getChatRoom ${e.message.toString()}")
            }
        }
        _currentChatRoom.postValue(chatRoom)
        return chatRoom
    }

    suspend fun getChatParticipants(chatParticipantsId:Long): ChatParticipants{
        lateinit var chatParticipants: ChatParticipants
        withContext(Dispatchers.IO) {
            try {
                chatParticipants = db.chatParticipantsDao().getChatParticipantsWithChatRoomId(chatParticipantsId)
                Log.e("ConnectTag", "getChatParticipants Laden hat geklappt")
            }catch (e:Exception){
                Log.e("ConnectTag", "getChatParticipants ${e.message.toString()}")
            }
        }
        _currentChatParticipants.postValue(chatParticipants)
        return chatParticipants
    }

    suspend fun sendMessage(message: Message){
        withContext(Dispatchers.IO) {
            try {
                db.messageDao().insert(message)
                Log.e("ConnectTag", "sendMessage Nachricht verschickt")
            }catch (e:Exception){
                Log.e("ConnectTag", "sendMessage ${e.message.toString()}")
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
                Log.e("ConnectTag", "updateChatRoomMessage Nachricht verschickt")
            }catch (e:Exception){
                Log.e("ConnectTag", "updateChatRoomMessage ${e.message.toString()}")
            }
        }
    }

    suspend fun loadUsersChatParticipantsLists(){
        withContext(Dispatchers.IO) {
            try {
                _usersChatRoomIdList.postValue(db.chatParticipantsDao().loadUsersChatLists(currentUserID))
                Log.e("ConnectTag", "loadUsersChatParticipantsLists wurde geladen")
            }catch (e:Exception){
                Log.e("ConnectTag", "loadUsersChatParticipantsLists ${e.message.toString()}")
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
                Log.e("ConnectTag", "loadChatRoomList wurde geladen")
            }catch (e:Exception){
                Log.e("ConnectTag", "loadChatRoomList ${e.message.toString()}")
            }
        }
    }

//    suspend fun getOneUserById(userID: Long): LiveData<User>{
//        lateinit var user : LiveData<User>
//        withContext(Dispatchers.IO) {
//            try {
//                user = db.userDao().getOneUser(userID)
//                Log.e("ConnectTag", "getOneUserById wurde geladen")
//            }catch (e:Exception){
//                Log.e("ConnectTag", "getOneUserById ${e.message.toString()}")
//            }
//        }
//        return user
//    }

}