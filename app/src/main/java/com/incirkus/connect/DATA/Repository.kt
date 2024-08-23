package com.incirkus.connect.DATA

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.incirkus.connect.DATA.Model.APIResponse
import com.incirkus.connect.DATA.Model.ChatParticipants
import com.incirkus.connect.DATA.Model.ChatRoom
import com.incirkus.connect.DATA.Model.Message
import com.incirkus.connect.DATA.Model.User
import com.incirkus.connect.DATA.Remote.CalendarApi
//import com.incirkus.connect.DATA.local.ConnectDatabase
import com.incirkus.connect.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class Repository() {

//    private val currentUserID: Long = 1
//
////    private var _currentUser = MutableLiveData<User>()
////    val currentUser: LiveData<User> = _currentUser
//
//
//    private var _userList = MutableLiveData<List<User>>()
//    //var userList:LiveData<List<User>> = _userList
//    val userList = db.userDao().getAllContacts(currentUserID)
//
////    private var _currentChatRoom = MutableLiveData<ChatRoom>()
////    val currentChatRoom : LiveData<ChatRoom> = _currentChatRoom
//
//    private var _currentChatParticipants = MutableLiveData<ChatParticipants>()
//    val currentChatParticipants : LiveData<ChatParticipants> = _currentChatParticipants
//
//    private var _usersChatRoomIdList = MutableLiveData<List<Long>>()
//    val usersChatRoomIdList : LiveData<List<Long>> = _usersChatRoomIdList
//
//    private var _usersChatRoomList = MutableLiveData<List<ChatRoom>>()
//    val usersChatRoomList : LiveData<List<ChatRoom>> = _usersChatRoomList

//    suspend fun insertCurrentUser(user: User){
//        withContext(Dispatchers.IO) {
//                try {
//                db.currentUserDao().insertUser(user)
//                Log.e("ConnectTag", "insertCurrentUser wurde gespeichert")
//                }catch (e:Exception){
//                    Log.e("ConnectTag", "insertCurrentUser ${e.message.toString()}")
//                }
//        }
//    }

//    suspend fun loadCurrentUserFromUserList(){
//
//        withContext(Dispatchers.IO) {
//            try {
//                var user: User = db.userDao().getloggedInUser(currentUserID)
//                Log.e("ConnectTag", "loadCurrentUserFromUserList wurde geladen")
//                insertCurrentUser(user)
//            }catch (e:Exception){
//                Log.e("ConnectTag", "loadCurrentUserFromUserList ${e.message.toString()}")
//            }
//        }
//        //_currentUser.postValue(user)
//
//    }

//    suspend fun loadCurrentUser(){
//        withContext(Dispatchers.IO) {
//            try {
//                _currentUser.postValue(db.currentUserDao().getCurrentUserDatatypeCurrentUser().currentUserToUser())
//                Log.e("ConnectTag", "loadCurrentUser wurde geladen")
//            }catch (e:Exception){
//                Log.e("ConnectTag", "loadCurrentUser ${e.message.toString()}")
//            }
//        }
//    }

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

//    suspend fun preload() {
//        withContext(Dispatchers.IO) {
//            val userlist = SampleData.users
//            try {
//                for (user in userlist) {
//                    db.userDao().insert(user)
//                    Log.e("ConnectTag", "preload geklappt")
//                }
//            } catch (e: Exception) {
//                Log.e("ConnectTag", "preload ${e.message.toString()}")
//            }
//        }
//    }

//    suspend fun createNewChatroom(currentUser: User, secondUser: User) {
//        withContext(Dispatchers.IO) {
//            val chatRoomName = "${currentUser.firstName?.replace(" ","")?.replace("-","")}${secondUser.firstName?.replace(" ","")?.replace("-","")}"
//            var chatRoomID: Long = 0L
//            var newChatRoom = ChatRoom(
//                chatRoomName = chatRoomName,
//                lastMessage = "Hi",
//                lastActivityTimestamp = System.currentTimeMillis(),
//                chatParticipantsId = 0L
//            )
//            try {
//                chatRoomID = db.chatRoomDao().insert(newChatRoom)
//                Log.e("ConnectTag", "createNewChatroom geklappt")
//            } catch (e: Exception) {
//                Log.e("ConnectTag", "createNewChatroom ${e.message.toString()}")
//            }
//
//            createNewChatParticipants(chatRoomID, currentUser, secondUser, newChatRoom)
//            getChatRoom(chatRoomID)
//        }
//    }

//    suspend fun createNewChatParticipants(chatRoomID: Long, currentUser: User, secondUser: User, newChatRoom: ChatRoom){
//        withContext(Dispatchers.IO) {
//            val newChatParticipants = ChatParticipants(
//                chatRoomId = chatRoomID,
//                user1Id = currentUser.userId,
//                user2Id = secondUser.userId
//            )
//            var chatParticipantsId: Long = 0L
//            try {
//                chatParticipantsId = db.chatParticipantsDao().insert(newChatParticipants)
//                Log.e("ConnectTag", "geklappt")
//            } catch (e: Exception) {
//                Log.e("ConnectTag", "createNewChatParticipants ${e.message.toString()}")
//            }
//            updateChatroom(chatParticipantsId, newChatRoom)
//            getChatParticipants(chatParticipantsId)
//        }
//    }

//    suspend fun updateChatroom(chatParticipantsId: Long, newChatRoom: ChatRoom){
//        withContext(Dispatchers.IO) {
//            newChatRoom.chatParticipantsId = chatParticipantsId
//            try {
//                db.chatRoomDao().update(newChatRoom)
//                Log.e("Connect", "updateChatroom geklappt")
//            } catch (e: Exception) {
//                Log.e("Connect", "updateChatroom ${e.message.toString()}")
//            }
//        }
//    }

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

//    suspend fun getChatRoom(chatRoomId:Long): ChatRoom{
//        lateinit var chatRoom: ChatRoom
//        withContext(Dispatchers.IO) {
//            try {
//                chatRoom = db.chatRoomDao().getChatRoomWithID(chatRoomId)
//                Log.e("ConnectTag", "getChatRoom Laden hat geklappt")
//            }catch (e:Exception){
//                Log.e("ConnectTag", "getChatRoom ${e.message.toString()}")
//            }
//        }
//        _currentChatRoom.postValue(chatRoom)
//        return chatRoom
//    }
//
//    suspend fun getChatParticipants(chatParticipantsId:Long): ChatParticipants{
//        lateinit var chatParticipants: ChatParticipants
//        withContext(Dispatchers.IO) {
//            try {
//                chatParticipants = db.chatParticipantsDao().getChatParticipantsWithChatRoomId(chatParticipantsId)
//                Log.e("ConnectTag", "getChatParticipants Laden hat geklappt")
//            }catch (e:Exception){
//                Log.e("ConnectTag", "getChatParticipants ${e.message.toString()}")
//            }
//        }
//        _currentChatParticipants.postValue(chatParticipants)
//        return chatParticipants
//    }
//
//    suspend fun sendMessage(message: Message){
//        withContext(Dispatchers.IO) {
//            try {
//                db.messageDao().insert(message)
//                Log.e("ConnectTag", "sendMessage Nachricht verschickt")
//            }catch (e:Exception){
//                Log.e("ConnectTag", "sendMessage ${e.message.toString()}")
//            }
//            updateChatRoomMessage(message)
//        }
//    }
//
//    suspend fun updateChatRoomMessage(message: Message){
//        withContext(Dispatchers.IO) {
//            try {
//                var updatedChatRoom: ChatRoom = _currentChatRoom.value!!
//                updatedChatRoom.lastMessage = message.messageText
//                db.chatRoomDao().update(updatedChatRoom)
//                Log.e("ConnectTag", "updateChatRoomMessage Nachricht verschickt")
//            }catch (e:Exception){
//                Log.e("ConnectTag", "updateChatRoomMessage ${e.message.toString()}")
//            }
//        }
//    }
//
//    suspend fun loadUsersChatParticipantsLists(){
//        withContext(Dispatchers.IO) {
//            try {
//                _usersChatRoomIdList.postValue(db.chatParticipantsDao().loadUsersChatLists(currentUserID))
//                Log.e("ConnectTag", "loadUsersChatParticipantsLists wurde geladen")
//            }catch (e:Exception){
//                Log.e("ConnectTag", "loadUsersChatParticipantsLists ${e.message.toString()}")
//            }
//        }
//        loadChatRoomList()
//    }
//
//    suspend fun loadChatRoomList(){
//        var chatRoomList: MutableList<ChatRoom> = mutableListOf()
//        withContext(Dispatchers.IO) {
//            try {
//                if (!_usersChatRoomIdList.value.isNullOrEmpty()){
//                    for (id in _usersChatRoomIdList.value!!){
//                        chatRoomList.add(db.chatRoomDao().getChatRoomWithID(id))
//                    }
//                }
//                _usersChatRoomList.postValue(chatRoomList)
//                Log.e("ConnectTag", "loadChatRoomList wurde geladen")
//            }catch (e:Exception){
//                Log.e("ConnectTag", "loadChatRoomList ${e.message.toString()}")
//            }
//        }
//    }
//
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



    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------  API----------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------


    suspend fun getHolidayList(): APIResponse {
        lateinit var holidayList: APIResponse
        withContext(Dispatchers.IO) {
            try {
                holidayList = CalendarApi.retrofitService.getallHolidays()

            } catch (e: Exception) {
                Log.e("APICALL", e.message.toString())
            }
        }
        return holidayList
    }


    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------FIREBASE-------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

//    private val firebaseDB = FirebaseFirestore.getInstance()
//    private val firebaseDataUser = firebaseDB.collection("User")
//
//    val firebasedb2 = Firebase.firestore

//    suspend fun getFirebaseDataUser(): MutableList<User>{
//        var firebaseUserList: MutableList<User>
//
//        try {
//            firebaseUserList = firebaseDataUser.get()
//                .addOnCompleteListener {
//                    if (it.isSuccessful){
//                        firebaseUserList = it.result.toObjects(List<User>::class.java)
//                    }
//                }
//        }catch (e:Exception){
//            firebaseUserList= emptyList<User>().toMutableList()
//        }
//
//        return firebaseUserList
//
//    }

//    suspend fun getFirebaseDataUser(): MutableList<User>{
//
//        var firebaseUserList: MutableList<User> = mutableListOf()
//
//        firebasedb2.collection("User")
//            .get()
//            .addOnSuccessListener {userList ->
//                for (user in userList){
//                    Log.d("Firebase", "${user.id} => ${user.data}")
//                    val user2 = User(
//                        userId = user.id,
//                        firstName = user.getString("firstName"),
//                        lastName = user.getString("lastName"),
//                        fullName = user.getString("fullName"),
//                        image = user.getString("image"),
//                        email = user.getString("email"),
//                        phoneNumber = user.getString("phoneNumber"),
//                        department = user.getString("department"),
//                    )
//                    firebaseUserList.add(user2)
//                }
//            }
//
//        return firebaseUserList
//
//    }

//    suspend fun getFirebaseDataUser(): MutableList<User> = suspendCancellableCoroutine { continuation ->
//        val firebaseUserList: MutableList<User> = mutableListOf()
//
//        firebasedb2.collection("User")
//            .whereNotEqualTo(FieldPath.documentId(), viewModel.currentUser.value?.uid)
//            .get()
//            .addOnSuccessListener { userList ->
//                for (user in userList) {
//                    Log.d("Firebase", "${user.id} => ${user.data}")
//                    val user2 = User(
//                        userId = user.id,
//                        firstName = user.getString("firstName"),
//                        lastName = user.getString("lastName"),
//                        fullName = user.getString("fullName"),
//                        image = user.getString("image"),
//                        email = user.getString("email"),
//                        phoneNumber = user.getString("phoneNumber"),
//                        department = user.getString("department"),
//                    )
//                    firebaseUserList.add(user2)
//                }
//                continuation.resume(firebaseUserList)  // Coroutine wird mit der User-Liste fortgesetzt
//            }
//            .addOnFailureListener { exception ->
//                continuation.resumeWithException(exception)  // Coroutine wird mit der Ausnahme fortgesetzt
//            }
//    }


    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------NEW APP--------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------


    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDB = FirebaseFirestore.getInstance()
    private val firebaseDataUser = firebaseDB.collection("User")

    val firebasedb2 = Firebase.firestore

    //User der gerade eingeloggt ist
    private var _currentFirebaseUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentFirebaseUser: LiveData<FirebaseUser?> = _currentFirebaseUser

    private var _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private var _firebaseUserList = MutableLiveData<List<User>>()
    val firebaseUserList : LiveData<List<User>> = _firebaseUserList

    private var _firebaseChatRoomList = MutableLiveData<List<ChatRoom>>()
    val firebaseChatRoomList : LiveData<List<ChatRoom>> = _firebaseChatRoomList

    private var _currentChatRoom = MutableLiveData<ChatRoom?>()
    val currentChatRoom: LiveData<ChatRoom?> = _currentChatRoom

    private var _firebaseCurrentMessageList = MutableLiveData<List<Message>>()
    val firebaseCurrentMessageList : LiveData<List<Message>> = _firebaseCurrentMessageList

    suspend fun setCurrentFirebaseUser(firebaseUser: FirebaseUser?)= suspendCancellableCoroutine { continuation->

        _currentFirebaseUser.postValue(firebaseUser)
        continuation.resume(Log.i("Firebase", "Repo: CurrentFirebaseUser: ${_currentFirebaseUser.value.toString()}"))

    }

    suspend fun logout()= suspendCancellableCoroutine { continuation->
        firebaseAuth.signOut()
        _currentFirebaseUser.value = null
        continuation.resume(Log.i("Firebase", "Repo: CurrentFirebaseUser: ${_currentFirebaseUser.value.toString()}"))
        _currentUser.postValue(User())
        _firebaseUserList.postValue(listOf())
        _firebaseChatRoomList.postValue(listOf())
    }

    suspend fun getFirebaseDataUser() = suspendCancellableCoroutine { continuation ->
        val firebaseUserList: MutableList<User> = mutableListOf()

        try {
            firebasedb2.collection("User")
                .whereNotEqualTo(FieldPath.documentId(), _currentFirebaseUser.value?.uid)
                .get()
                .addOnSuccessListener { userList ->
                    for (user in userList) {
                        Log.d("Firebase", "${user.id} => ${user.data}")
                        val user2 = User(
                            userId = user.id,
                            firstName = user.getString("firstName"),
                            lastName = user.getString("lastName"),
                            fullName = user.getString("fullName"),
                            image = user.getString("image"),
                            email = user.getString("email"),
                            phoneNumber = user.getString("phoneNumber"),
                            department = user.getString("department"),
                        )
                        firebaseUserList.add(user2)
                    }
                    continuation.resume(_firebaseUserList.postValue(firebaseUserList))  // Coroutine wird mit der User-Liste fortgesetzt
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)  // Coroutine wird mit der Ausnahme fortgesetzt
                }

            Log.i("Firebase", "Repo: getFirebaseDataUser: ${_firebaseUserList.toString()}")

        }catch (e:Exception){
            Log.i("Firebase", "Repo: getFirebaseDataUser: ${e.toString()}")
        }

    }

    suspend fun setCurrentUser()= suspendCancellableCoroutine { continuation ->

        try {
            val currentUserRef = firebasedb2.collection("User").document(_currentFirebaseUser.value?.uid!!)
            currentUserRef.get().addOnSuccessListener {
                val user2 = it.toObject<User>()
                if (user2 != null) {
                    user2.userId = _currentFirebaseUser.value?.uid!!
                }
                continuation.resume(_currentUser.postValue(user2))  // Coroutine wird mit der User-Liste fortgesetzt
            }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)  // Coroutine wird mit der Ausnahme fortgesetzt
                }

            Log.i("Firebase", "Repo: setCurrentUser: ${_currentUser.toString()}")

        }catch (e:Exception){
            Log.i("Firebase", "Repo: setCurrentUser: ${e.toString()}")
        }
    }


    suspend fun getFirebaseDataChatRooms() = suspendCancellableCoroutine { continuation ->
        val firebaseChatRoomList: MutableList<ChatRoom> = mutableListOf()

        try {
            _currentFirebaseUser.value?.uid?.let {
                firebasedb2.collection("ChatRooms")
                    .whereArrayContains("chatParticipants", it)
                    .get()
                    .addOnSuccessListener { ChatRoomList ->
                        for (chatRoom in ChatRoomList) {
                            Log.d("Firebase", "${chatRoom.id} => ${chatRoom.data}")

                            val arrayList: ArrayList<String> = chatRoom.get("chatParticipants") as ArrayList<String>
                            val mutableList: MutableList<String?> = arrayList.toMutableList()

                            val chatRoom2 = ChatRoom(
                                chatRoomId = chatRoom.id,
                                chatRoomName = chatRoom.getString("chatRoomName"),
                                lastMessage= chatRoom.getString("lastMessage"),
                                lastActivityTimestamp = chatRoom.getLong("lastActivityTimestamp"),
                                chatParticipants = mutableList
                            )


                            firebaseChatRoomList.add(chatRoom2)
                        }
                        continuation.resume(_firebaseChatRoomList.postValue(firebaseChatRoomList))  // Coroutine wird mit der User-Liste fortgesetzt
                    }
                    .addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)  // Coroutine wird mit der Ausnahme fortgesetzt
                    }
            }

            Log.i("Firebase", "Repo: getFirebaseDataChatRooms: ${_firebaseChatRoomList.toString()}")

        }catch (e:Exception){
            Log.i("Firebase", "Repo: getFirebaseDataChatRooms: ${e.toString()}")
        }

    }

    suspend fun getCurrentChatRoom(chatRoomId: String)= suspendCancellableCoroutine { continuation ->

        try {
            val currentChatRoomRef = firebasedb2.collection("ChatRooms").document(chatRoomId)
            currentChatRoomRef.get().addOnSuccessListener {
                val chatRoom = it.toObject<ChatRoom>()
                if (chatRoom != null) {
                    chatRoom.chatRoomId = chatRoomId
                }
                continuation.resume(_currentChatRoom.postValue(chatRoom))  // Coroutine wird mit der User-Liste fortgesetzt
            }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)  // Coroutine wird mit der Ausnahme fortgesetzt
                }

            Log.i("Firebase", "Repo: getCurrentChatRoom: ${_currentChatRoom.toString()}")

        }catch (e:Exception){
            Log.i("Firebase", "Repo: getCurrentChatRoom: ${e.toString()}")
        }
    }

    fun setCurrentChatroom(chatRoom: ChatRoom){
        _currentChatRoom.postValue(chatRoom)
    }

    suspend fun getFirebaseDataCurrentMessageList() = suspendCancellableCoroutine<Unit> { continuation ->
        val firebaseCurrentMessageList: MutableList<Message> = mutableListOf()

        try {
            _currentChatRoom.value?.chatRoomId.let { chatRoomId ->
                val listenerRegistration = firebasedb2.collection("Messages")
                    .whereEqualTo("chatRoomId", chatRoomId)
                    .addSnapshotListener { messageList, e ->
                        if (e != null) {
                            Log.i("Firebase", "Repo getRealtimeFirebaseDataCurrentMessageList Listen failed.", e)
                            if (!continuation.isCompleted) {
                                continuation.resumeWithException(e)
                            }
                            return@addSnapshotListener
                        }

                        firebaseCurrentMessageList.clear() // Alte Liste leeren
                        for (message in messageList!!) {
                            Log.d("Firebase", "Repo: Message: ${message.id} => ${message.data}")

                            val message2 = Message(
                                messageId = message.id,
                                chatRoomId = message.getString("chatRoomId"),
                                senderId = message.getString("senderId"),
                                messageText = message.getString("messageText"),
                                timestamp = message.getLong("timestamp"),
                                messageStatus = message.getString("messageStatus")
                            )

                            firebaseCurrentMessageList.add(message2)
                        }
                        val firebaseCurrentMessageList2 = firebaseCurrentMessageList.sortedBy { it.timestamp }

                        _firebaseCurrentMessageList.postValue(firebaseCurrentMessageList2)

                        // Coroutine das erste Mal fortsetzen, falls es noch nicht abgeschlossen ist
                        if (!continuation.isCompleted) {
                            continuation.resume(Unit)
                        }

                        Log.i("Firebase", "Repo getRealtimeFirebaseDataCurrentMessageList: ${firebaseCurrentMessageList2.toString()}")
                    }

                // Falls die Coroutine storniert wird, Listener entfernen
                continuation.invokeOnCancellation {
                    listenerRegistration.remove()
                    Log.i("Firebase", "Repo getRealtimeFirebaseDataCurrentMessageList: Listener wurde entfernt.")
                }
            }
        } catch (e: Exception) {
            Log.i("Firebase", "Repo: getRealtimeFirebaseDataCurrentMessageList: ${e.toString()}")
            if (!continuation.isCompleted) {
                continuation.resumeWithException(e)
            }
        }
    }



}