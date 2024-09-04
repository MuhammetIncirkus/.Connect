package com.incirkus.connect.DATA

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.incirkus.connect.DATA.Model.APIResponse
import com.incirkus.connect.DATA.Model.Attachment
import com.incirkus.connect.DATA.Model.ChatRoom
import com.incirkus.connect.DATA.Model.Holiday
import com.incirkus.connect.DATA.Model.LeaveRequest
import com.incirkus.connect.DATA.Model.Message
import com.incirkus.connect.DATA.Model.User
import com.incirkus.connect.DATA.Remote.CalendarApi
//import com.incirkus.connect.DATA.local.ConnectDatabase
import com.incirkus.connect.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class Repository() {

    //Used Variables:
    //Initialising Firebase
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDB = FirebaseFirestore.getInstance()
    private val firebasedb2 = Firebase.firestore

    //Firebase User who is currently logged in as LiveData
    private var _currentFirebaseUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentFirebaseUser: LiveData<FirebaseUser?> = _currentFirebaseUser
    //User who is currently logged in as LiveData
    private var _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser
    //UserList with all colleagues without currentUser
    private var _firebaseUserList = MutableLiveData<List<User>>()
    val firebaseUserList : LiveData<List<User>> = _firebaseUserList
    //List of all chatRooms in which the currentUser is involved
    private var _firebaseChatRoomList = MutableLiveData<List<ChatRoom>>()
    val firebaseChatRoomList : LiveData<List<ChatRoom>> = _firebaseChatRoomList
    //the actual chatRoom
    private var _currentChatRoom = MutableLiveData<ChatRoom?>()
    val currentChatRoom: LiveData<ChatRoom?> = _currentChatRoom
    //List of all sending messages from all Users
    private var _firebaseMessageList = MutableLiveData<List<Message>>()
    val firebaseMessageList : LiveData<List<Message>> = _firebaseMessageList
    //List of all holidayRequests from currentUser
    private var _firebaseLeaveRequestList = MutableLiveData<List<LeaveRequest>>()
    val firebaseLeaveRequestList : LiveData<List<LeaveRequest>> = _firebaseLeaveRequestList
    //List of all sending attachments from all Users
    private var _firebaseAttachmentList = MutableLiveData<List<Attachment>>()
    val firebaseAttachmentList : LiveData<List<Attachment>> = _firebaseAttachmentList
    // List of all public Holidays coming from api as LiveData
    private var _firebaseHolidayList = MutableLiveData<List<Holiday>>()
    val firebaseHolidayList : LiveData<List<Holiday>> = _firebaseHolidayList
    // List of all public Holidays coming from api
    var holidayList: MutableList<Holiday> = mutableListOf()

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------  API----------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Retrieves a list of holidays from a remote API asynchronously.
     * The function executes the network request on an IO dispatcher to avoid blocking the main thread. If the request is successful, the list of holidays is returned.
     * If an error occurs, the function logs the error and returns an uninitialized or empty response.
     *
     *  ---
     *
     * Ruft asynchron eine Liste von Feiertagen von einer externen API ab.
     * Die Funktion führt die Netzwerkanfrage auf einem IO-Dispatcher aus, um eine Blockierung des Haupt-Threads zu vermeiden. Bei erfolgreicher Anfrage wird die Liste
     * der Feiertage zurückgegeben. Im Falle eines Fehlers wird der Fehler protokolliert und eine nicht initialisierte oder leere Antwort zurückgegeben.
     *
     * @return APIResponse: The list of holidays retrieved from the API. In case of a failure, an empty or uninitialized response may be returned.
     *                      Die Liste der von der API abgerufenen Feiertage. Im Falle eines Fehlers kann eine leere oder nicht initialisierte Antwort zurückgegeben werden.
     *
     */
    suspend fun getHolidayList(): APIResponse {
        lateinit var holidayList: APIResponse
        withContext(Dispatchers.IO) {
            try {
                holidayList = CalendarApi.retrofitService.getallHolidays()
                Log.e("CONNECT", "Repository: getHolidayList: List loaded")
            } catch (e: Exception) {
                Log.e("CONNECT", "Repository: getHolidayList: ${e.message.toString()}")
            }
        }
        return holidayList
    }


    /**
     * Retrieves a list of holidays for specified states from a remote API asynchronously.
     * The function executes the network request on an IO dispatcher to avoid blocking the main thread.
     * If the request is successful, the list of holidays is returned. If an error occurs, the function
     * logs the error and returns an uninitialized or empty response.
     *
     * ---
     *
     * Ruft asynchron eine Liste von Feiertagen für bestimmte Bundesländer von einer externen API ab.
     * Die Funktion führt die Netzwerkanfrage auf einem IO-Dispatcher aus, um eine Blockierung des Haupt-Threads zu vermeiden.
     * Bei erfolgreicher Anfrage wird die Liste der Feiertage zurückgegeben. Im Falle eines Fehlers wird der Fehler protokolliert
     * und eine nicht initialisierte oder leere Antwort zurückgegeben.
     *
     * @param states: String - A comma-separated list of state codes for which the holidays should be retrieved.
     *                 Eine durch Kommas getrennte Liste von Bundeslandcodes, für die die Feiertage abgerufen werden sollen.
     *
     * @return APIResponse: The list of holidays retrieved from the API. In case of a failure, an empty or uninitialized response may be returned.
     *                      Die Liste der von der API abgerufenen Feiertage. Im Falle eines Fehlers kann eine leere oder nicht initialisierte Antwort zurückgegeben werden.
     */
    suspend fun getHolidayListForSomeStates(states: String): APIResponse {
        lateinit var holidayList: APIResponse
        withContext(Dispatchers.IO) {
            try {
                holidayList = CalendarApi.retrofitService.getStates("2024,2025,2026",states)
                Log.e("CONNECT", "Repository: getHolidayListForSomeStates: List loaded")
            } catch (e: Exception) {
                Log.e("CONNECT", "Repository: getHolidayListForSomeStates: ${e.message.toString()}")
            }
        }
        return holidayList
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------  API----------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------


    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------FIREBASE-------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    suspend fun setCurrentFirebaseUser(firebaseUser: FirebaseUser?)= suspendCancellableCoroutine { continuation->
        _currentFirebaseUser.postValue(firebaseUser)
        continuation.resume(Log.i("Firebase", "Repo: CurrentFirebaseUser: ${_currentFirebaseUser.value.toString()}"))
    }

    fun logout() {
        firebaseAuth.signOut()
        _currentFirebaseUser.value = null
        Log.i("CONNECT", "Repository: logout: _currentFirebaseUser: ${_currentFirebaseUser.value.toString()}")
        _currentUser.value = null
        Log.i("CONNECT", "Repository: logout: _currentUser: ${_currentUser.value.toString()}")
        _firebaseUserList.value = listOf()
        Log.i("CONNECT", "Repository: logout: _firebaseUserList: ${_firebaseUserList.value.toString()}")
        _firebaseChatRoomList.value = listOf()
        Log.i("CONNECT", "Repository: logout: _firebaseChatRoomList: ${_firebaseChatRoomList.value.toString()}")
        _currentChatRoom.value = null
        Log.i("CONNECT", "Repository: logout: _currentChatRoom: ${_currentChatRoom.value.toString()}")
        _firebaseMessageList.value = listOf()
        Log.i("CONNECT", "Repository: logout: _firebaseMessageList: ${_firebaseMessageList.value.toString()}")
        _firebaseLeaveRequestList.value = listOf()
        Log.i("CONNECT", "Repository: logout: _firebaseLeaveRequestList: ${_firebaseLeaveRequestList.value.toString()}")
        _firebaseAttachmentList.value = listOf()
        Log.i("CONNECT", "Repository: logout: _firebaseAttachmentList: ${_firebaseAttachmentList.value.toString()}")
        _firebaseHolidayList.value = listOf()
        Log.i("CONNECT", "Repository: logout: _firebaseHolidayList: ${_firebaseHolidayList.value.toString()}")
        holidayList = mutableListOf()
        Log.i("CONNECT", "Repository: logout: holidayList: ${holidayList}")
    }

    suspend fun getFirebaseDataUser() = suspendCancellableCoroutine { continuation ->
        val firebaseUserList: MutableList<User> = mutableListOf()

        try {
            firebaseDB.collection("User")
                .whereNotEqualTo(FieldPath.documentId(), _currentFirebaseUser.value?.uid)
                .addSnapshotListener { userList, e ->
                    if (e != null) {
                        Log.i("CONNECT", "Repository: getFirebaseDataUser: Listen failed.", e)
                        if (!continuation.isCompleted) {
                            continuation.resumeWithException(e)
                        }
                        return@addSnapshotListener
                    }
                    if (userList != null) {
                        for (user in userList) {
                            Log.d("CONNECT", "Repository: getFirebaseDataUser: user: ${user.id} => ${user.data}")
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
                    }
                    _firebaseUserList.postValue(firebaseUserList)
                    if (!continuation.isCompleted) {
                        continuation.resume(Unit)
                    }
                }
            Log.i("CONNECT", "Repository: getFirebaseDataUser: ${_firebaseUserList.value.toString()}")
            continuation.invokeOnCancellation {
                if (it != null) {
                    continuation.resumeWithException(it)
                }
                Log.i("Firebase", "Repo getRealtimeFirebaseDataCurrentMessageList: Listener wurde entfernt.")
            }

        }catch (e:Exception){
            Log.i("CONNECT", "Repository: getFirebaseDataUser: Exception", e)
        }
    }

    suspend fun setCurrentUser()= suspendCancellableCoroutine { continuation ->

        try {
            val currentUserRef = firebasedb2.collection("User").document(_currentFirebaseUser.value?.uid!!)
            currentUserRef
                .addSnapshotListener {currentUser, e ->
                    if (e != null) {
                        Log.d("CONNECT", "Repository: setCurrentUser: Listen failed.", e)
                        if (!continuation.isCompleted) {
                            continuation.resumeWithException(e)
                        }
                        return@addSnapshotListener
                    }
                val user2 = currentUser?.toObject<User>()
                if (user2 != null) {
                    user2.userId = _currentFirebaseUser.value?.uid!!
                    _currentUser.postValue(user2)
                }
                    if (!continuation.isCompleted) {
                        continuation.resume(Unit)
                    }
            }
            Log.i("Firebase", "Repo: setCurrentUser: ${_currentUser.value.toString()}")

            continuation.invokeOnCancellation {
                if (it != null) {
                    continuation.resumeWithException(it)
                }
                Log.i("Firebase", "Repo getRealtimeFirebaseDataCurrentMessageList: Listener wurde entfernt.")
            }

        }catch (e:Exception){
            Log.i("Firebase", "Repo: setCurrentUser:  Exception", e)
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
        _currentChatRoom.value = chatRoom
    }



    suspend fun getMessageList() = suspendCancellableCoroutine<Unit> { continuation ->
        val firebaseCurrentMessageList: MutableList<Message> = mutableListOf()

        try {

                val listenerRegistration = firebaseDB.collection("Messages")
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
                                messageStatus = message.getString("messageStatus"),
                            )

                            firebaseCurrentMessageList.add(message2)
                        }
                        val firebaseCurrentMessageList2 = firebaseCurrentMessageList.sortedBy { it.timestamp }

                        _firebaseMessageList.postValue(firebaseCurrentMessageList2)

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

        } catch (e: Exception) {
            Log.i("Firebase", "Repo: getRealtimeFirebaseDataCurrentMessageList: ${e.toString()}")
            if (!continuation.isCompleted) {
                continuation.resumeWithException(e)
            }
        }
    }

    fun clearMessagelist(){
        val emptyMessageList:List<Message> = listOf()
        _firebaseMessageList.postValue(emptyMessageList)
    }

    suspend fun getChatRoomList() = suspendCancellableCoroutine<Unit> { continuation ->
        var isResumed = false // Flag, um mehrfaches Fortsetzen zu verhindern

        val listenerRegistration = firebaseDB.collection("ChatRooms")
            .whereArrayContains("chatParticipants", _currentFirebaseUser.value!!.uid)
            .addSnapshotListener { chatRoomList, e ->
                if (e != null) {
                    Log.i("Firebase", "Repo: getChatRoomListNew: Listen failed: ${e.toString()}")
                    if (!isResumed) {
                        continuation.resumeWithException(e) // Fehler an die Coroutine weitergeben
                        isResumed = true
                    }
                    return@addSnapshotListener
                }

                val chatRoomList2: MutableList<ChatRoom> = mutableListOf()
                for (chatRoom in chatRoomList!!) {
                    val chatRoom2 = chatRoom.toObject<ChatRoom>()
                    Log.i("Firebase", "Repo: getChatRoomListNew: Listen passed: ${chatRoom.data}")
                    chatRoomList2.add(chatRoom2)
                }
                Log.i("Firebase", "Repo: getChatRoomListNew: Listen passed: List: ${chatRoomList2}")
                _firebaseChatRoomList.value = chatRoomList2


                if (!isResumed) {
                    continuation.resume(Unit)
                    isResumed = true
                }
            }

        // Stelle sicher, dass der Listener abgemeldet wird, wenn die Coroutine abgebrochen wird
        continuation.invokeOnCancellation {
            listenerRegistration.remove()
        }
    }




    suspend fun getFirebaseDataLeaveRequests() = suspendCancellableCoroutine<Unit> { continuation ->
        val currentFirebaseLeaveRequestList: MutableList<LeaveRequest> = mutableListOf()

        try {
            val listenerRegistration = firebaseDB.collection("LeaveRequest")
                .whereEqualTo("userId", _currentFirebaseUser.value?.uid)
                .addSnapshotListener { leaveRequestList, e ->
                    if (e != null) {
                        Log.i("Firebase", "Repo getFirebaseDataLeaveRequests Listen failed.", e)
                        if (!continuation.isCompleted) {
                            continuation.resumeWithException(e)
                        }
                        return@addSnapshotListener
                    }

                    currentFirebaseLeaveRequestList.clear() // Alte Liste leeren
                    for (leaveRequest in leaveRequestList!!) {
                        Log.d("Firebase", "Repo: leaveRequest: ${leaveRequest.id} => ${leaveRequest.data}")

                        val leaveRequest2 = LeaveRequest(
                            requestId = leaveRequest.id,
                            userId = leaveRequest.getString("userId"),
                            startDate = leaveRequest.getLong("startDate"),
                            endDate = leaveRequest.getLong("endDate"),
                            status = leaveRequest.getString("status"),
                        )

                        currentFirebaseLeaveRequestList.add(leaveRequest2)
                    }


                    _firebaseLeaveRequestList.postValue(currentFirebaseLeaveRequestList)

                    // Coroutine das erste Mal fortsetzen, falls es noch nicht abgeschlossen ist
                    if (!continuation.isCompleted) {
                        continuation.resume(Unit)
                    }

                    Log.i("Firebase", "Repo getFirebaseDataLeaveRequests: ${currentFirebaseLeaveRequestList.toString()}")
                }

            // Falls die Coroutine storniert wird, Listener entfernen
            continuation.invokeOnCancellation {
                listenerRegistration.remove()
                Log.i("Firebase", "Repo getFirebaseDataLeaveRequests: Listener wurde entfernt.")
            }

        } catch (e: Exception) {
            Log.i("Firebase", "Repo: getFirebaseDataLeaveRequests: ${e.toString()}")
            if (!continuation.isCompleted) {
                continuation.resumeWithException(e)
            }
        }
    }

    suspend fun getFirebaseAttachments() = suspendCancellableCoroutine<Unit> { continuation ->
        val firebaseCurrentAttachmentList: MutableList<Attachment> = mutableListOf()

        try {

            val listenerRegistration = firebaseDB.collection("Attachments")
                .addSnapshotListener { attachmentList, e ->
                    if (e != null) {
                        Log.i("Firebase", "Repo getFirebaseAttachments Listen failed.", e)
                        if (!continuation.isCompleted) {
                            continuation.resumeWithException(e)
                        }
                        return@addSnapshotListener
                    }

                    firebaseCurrentAttachmentList.clear() // Alte Liste leeren
                    for (attachment in attachmentList!!) {
                        Log.d("Firebase", "Repo: Attachment: ${attachment.id} => ${attachment.data}")

                        val attachment2 = Attachment(
                            attachmentId = attachment.id,
                            senderID = attachment.getString("senderID"),
                            attachmentName = attachment.getString("attachmentName"),
                            attachmentType = attachment.getString("attachmentType"),
                            chatRoomId = attachment.getString("chatRoomId"),
                            timestamp = attachment.getLong("timestamp"),
                            path = attachment.getString("path"),
                        )

                        firebaseCurrentAttachmentList.add(attachment2)
                    }
                    val firebaseCurrentAttachmentList2 = firebaseCurrentAttachmentList.sortedBy { it.timestamp }

                    _firebaseAttachmentList.postValue(firebaseCurrentAttachmentList2)

                    // Coroutine das erste Mal fortsetzen, falls es noch nicht abgeschlossen ist
                    if (!continuation.isCompleted) {
                        continuation.resume(Unit)
                    }

                    Log.i("Firebase", "Repo getFirebaseAttachments: ${firebaseCurrentAttachmentList2.toString()}")
                }

            // Falls die Coroutine storniert wird, Listener entfernen
            continuation.invokeOnCancellation {
                listenerRegistration.remove()
                Log.i("Firebase", "Repo getFirebaseAttachments: Listener wurde entfernt.")
            }

        } catch (e: Exception) {
            Log.i("Firebase", "Repo: getRealtimeFirebaseDataCurrentMessageList: ${e.toString()}")
            if (!continuation.isCompleted) {
                continuation.resumeWithException(e)
            }
        }
    }

    suspend fun getFirebaseHolidaylist() = suspendCancellableCoroutine<Unit> { continuation ->
        val firebaseHolidayList2: MutableList<Holiday> = mutableListOf()

        try {

            val listenerRegistration = firebaseDB.collection("User/${_currentFirebaseUser.value!!.uid}/HolidayList")
                .addSnapshotListener { holidayList2, e ->
                    if (e != null) {
                        Log.i("Firebase", "Repo getFirebaseHolidaylist Listen failed.", e)
                        if (!continuation.isCompleted) {
                            continuation.resumeWithException(e)
                        }
                        return@addSnapshotListener
                    }

                    firebaseHolidayList2.clear()
                    holidayList.clear()// Alte Liste leeren
                    for (holiday in holidayList2!!) {
                        Log.d("Firebase", "Repo: Holiday: ${holiday.id} => ${holiday.data}")


                        val holiday2: Holiday = Holiday(
                            holidayId= holiday.id,
                            holidayName = holiday.getString("holidayName"),
                            holidayRegion = holiday.getString("holidayRegion"),
                            holidayDay = holiday.getLong("holidayDay")?.toInt(),
                            holidayMonth = holiday.getLong("holidayMonth")?.toInt(),
                            holidayYear = holiday.getLong("holidayYear")?.toInt(),
                            comment= holiday.getString("comment"),
                            augsburg = holiday.getBoolean("augsburg"),
                            katholisch = holiday.getBoolean("katholisch")
                        )

                        firebaseHolidayList2.add(holiday2)
                        holidayList.add(holiday2)
                    }

                    _firebaseHolidayList.postValue(firebaseHolidayList2)

                    if (holidayList.isEmpty()){
//                        viewmodel.getListForHolidaysManual()
                    }

                    // Coroutine das erste Mal fortsetzen, falls es noch nicht abgeschlossen ist
                    if (!continuation.isCompleted) {
                        continuation.resume(Unit)
                    }

                    Log.i("Firebase", "Repo getFirebaseHolidaylist: ${firebaseHolidayList2.toString()}")
                }

            // Falls die Coroutine storniert wird, Listener entfernen
            continuation.invokeOnCancellation {
                listenerRegistration.remove()
                Log.i("Firebase", "Repo getFirebaseHolidaylist: Listener wurde entfernt.")
            }

        } catch (e: Exception) {
            Log.i("Firebase", "Repo: getFirebaseHolidaylist: ${e.toString()}")
            if (!continuation.isCompleted) {
                continuation.resumeWithException(e)
            }
        }
    }





}