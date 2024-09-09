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
    val firebaseUserList: LiveData<List<User>> = _firebaseUserList

    //List of all chatRooms in which the currentUser is involved
    private var _firebaseChatRoomList = MutableLiveData<List<ChatRoom>>()
    val firebaseChatRoomList: LiveData<List<ChatRoom>> = _firebaseChatRoomList

    //the actual chatRoom
    private var _currentChatRoom = MutableLiveData<ChatRoom?>()
    val currentChatRoom: LiveData<ChatRoom?> = _currentChatRoom

    //List of all sending messages from all Users
    private var _firebaseMessageList = MutableLiveData<List<Message>>()
    val firebaseMessageList: LiveData<List<Message>> = _firebaseMessageList

    //List of all holidayRequests from currentUser
    private var _firebaseLeaveRequestList = MutableLiveData<List<LeaveRequest>>()
    val firebaseLeaveRequestList: LiveData<List<LeaveRequest>> = _firebaseLeaveRequestList

    //List of all sending attachments from all Users
    private var _firebaseAttachmentList = MutableLiveData<List<Attachment>>()
    val firebaseAttachmentList: LiveData<List<Attachment>> = _firebaseAttachmentList

    // List of all public Holidays coming from api as LiveData
    private var _firebaseHolidayList = MutableLiveData<List<Holiday>>()
    val firebaseHolidayList: LiveData<List<Holiday>> = _firebaseHolidayList

    // List of all public Holidays coming from api
    var holidayList: MutableList<Holiday> = mutableListOf()

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------API----------------------------------------------------------------------------
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
                holidayList = CalendarApi.retrofitService.getStates("2024,2025,2026", states)
                Log.e("CONNECT", "Repository: getHolidayListForSomeStates: List loaded")
            } catch (e: Exception) {
                Log.e("CONNECT", "Repository: getHolidayListForSomeStates: ${e.message.toString()}")
            }
        }
        return holidayList
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------/API----------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------


    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------FIREBASE-------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Sets the current Firebase user asynchronously and logs the user's value.
     * The function posts the given Firebase user to the `_currentFirebaseUser` LiveData object and resumes the coroutine
     * while ensuring it can be cancelled if needed. The result is logged using the user's current value.
     *
     * ---
     *
     * Setzt asynchron den aktuellen Firebase-Benutzer und protokolliert den Wert des Benutzers.
     * Die Funktion postet den übergebenen Firebase-Benutzer in das LiveData-Objekt `_currentFirebaseUser` und setzt die Coroutine fort,
     * während sichergestellt wird, dass sie bei Bedarf abgebrochen werden kann. Das Ergebnis wird unter Verwendung des aktuellen Werts des Benutzers protokolliert.
     *
     * @param firebaseUser: FirebaseUser? - The Firebase user to be set as the current user, can be null.
     *                      Der Firebase-Benutzer, der als aktueller Benutzer gesetzt werden soll, kann null sein.
     *
     * @return Unit: The function does not return a value. The coroutine is resumed after posting the Firebase user and logging.
     *               Die Funktion gibt keinen Wert zurück. Die Coroutine wird nach dem Posten des Firebase-Benutzers und dem Protokollieren fortgesetzt.
     */
    suspend fun setCurrentFirebaseUser(firebaseUser: FirebaseUser?) =
        suspendCancellableCoroutine { continuation ->
            _currentFirebaseUser.postValue(firebaseUser)
            continuation.resume(
                Log.i(
                    "Firebase",
                    "Repo: CurrentFirebaseUser: ${_currentFirebaseUser.value.toString()}"
                )
            )
        }

    /**
     * Logs the user out by signing out from Firebase authentication and resetting all relevant LiveData properties.
     * The function clears user-related data, including chat rooms, messages, leave requests, attachments, holidays, and user lists.
     * Each step of the logout process is logged, indicating the state of various LiveData objects and lists after the reset.
     *
     * ---
     *
     * Meldet den Benutzer ab, indem er von Firebase Authentication abgemeldet wird und alle relevanten LiveData-Eigenschaften zurückgesetzt werden.
     * Die Funktion löscht benutzerbezogene Daten, einschließlich Chatrooms, Nachrichten, Urlaubsanfragen, Anhänge, Feiertage und Benutzerlisten.
     * Jeder Schritt des Abmeldevorgangs wird protokolliert, wobei der Zustand der verschiedenen LiveData-Objekte und Listen nach dem Zurücksetzen angezeigt wird.
     *
     */
    fun logout() {
        firebaseAuth.signOut()
        _currentFirebaseUser.value = null
        Log.i(
            "CONNECT",
            "Repository: logout: _currentFirebaseUser: ${_currentFirebaseUser.value.toString()}"
        )
        _currentUser.value = null
        Log.i("CONNECT", "Repository: logout: _currentUser: ${_currentUser.value.toString()}")
        _firebaseUserList.value = listOf()
        Log.i(
            "CONNECT",
            "Repository: logout: _firebaseUserList: ${_firebaseUserList.value.toString()}"
        )
        _firebaseChatRoomList.value = listOf()
        Log.i(
            "CONNECT",
            "Repository: logout: _firebaseChatRoomList: ${_firebaseChatRoomList.value.toString()}"
        )
        _currentChatRoom.value = null
        Log.i(
            "CONNECT",
            "Repository: logout: _currentChatRoom: ${_currentChatRoom.value.toString()}"
        )
        _firebaseMessageList.value = listOf()
        Log.i(
            "CONNECT",
            "Repository: logout: _firebaseMessageList: ${_firebaseMessageList.value.toString()}"
        )
        _firebaseLeaveRequestList.value = listOf()
        Log.i(
            "CONNECT",
            "Repository: logout: _firebaseLeaveRequestList: ${_firebaseLeaveRequestList.value.toString()}"
        )
        _firebaseAttachmentList.value = listOf()
        Log.i(
            "CONNECT",
            "Repository: logout: _firebaseAttachmentList: ${_firebaseAttachmentList.value.toString()}"
        )
        _firebaseHolidayList.value = listOf()
        Log.i(
            "CONNECT",
            "Repository: logout: _firebaseHolidayList: ${_firebaseHolidayList.value.toString()}"
        )
        holidayList = mutableListOf()
        Log.i("CONNECT", "Repository: logout: holidayList: ${holidayList}")
    }

    /**
     * Retrieves a list of users from a Firebase Firestore collection asynchronously and updates the internal LiveData object.
     * The function listens to changes in the "User" collection and filters out the current user by their UID. It converts the
     * Firebase documents into `User` objects and adds them to the list, which is then posted to the LiveData `_firebaseUserList`.
     * If an error occurs during the listener registration or data fetching process, the error is logged and the coroutine resumes with an exception.
     * The listener is removed upon cancellation of the coroutine.
     *
     * ---
     *
     * Ruft asynchron eine Liste von Benutzern aus einer Firebase Firestore-Sammlung ab und aktualisiert das interne LiveData-Objekt.
     * Die Funktion hört auf Änderungen in der "User"-Sammlung und filtert den aktuellen Benutzer anhand seiner UID heraus.
     * Die Firebase-Dokumente werden in `User`-Objekte umgewandelt und der Liste hinzugefügt, die dann an das LiveData `_firebaseUserList` übergeben wird.
     * Tritt während der Listener-Registrierung oder des Datenabrufs ein Fehler auf, wird dieser protokolliert, und die Coroutine wird mit einer Ausnahme fortgesetzt.
     * Der Listener wird bei einer Stornierung der Coroutine entfernt.
     *
     * @return Unit: The function does not return a value directly. The coroutine is resumed after the data is fetched or if an exception occurs.
     *               Die Funktion gibt keinen Wert direkt zurück. Die Coroutine wird nach dem Abruf der Daten oder bei einer Ausnahme fortgesetzt.
     */
    suspend fun getFirebaseDataUser() = suspendCancellableCoroutine { continuation ->
        val firebaseUserList: MutableList<User> = mutableListOf()

        try {
            val listenerRegistration = firebaseDB.collection("User")
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
                            Log.d(
                                "CONNECT",
                                "Repository: getFirebaseDataUser: user: ${user.id} => ${user.data}"
                            )
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

                    Log.i(
                        "CONNECT",
                        "Repository: getFirebaseDataUser: ${_firebaseUserList.value.toString()}"
                    )
                }

            continuation.invokeOnCancellation {
                listenerRegistration.remove()
                Log.i("CONNECT", "Repository: getFirebaseDataUser: Listener wurde entfernt.")
            }

        } catch (e: Exception) {
            Log.i("CONNECT", "Repository: getFirebaseDataUser: Exception", e)
        }
    }

    /**
     * Retrieves the current user data from Firebase Firestore asynchronously and updates the internal LiveData object `_currentUser`.
     * The function listens for changes to the user's document in Firestore and converts the document into a `User` object.
     * It updates the LiveData `_currentUser` with the fetched user data. If an error occurs during the listener registration or data retrieval,
     * it is logged and the coroutine resumes with an exception. The listener is removed when the coroutine is cancelled.
     *
     * ---
     *
     * Ruft asynchron die aktuellen Benutzerdaten aus Firebase Firestore ab und aktualisiert das interne LiveData-Objekt `_currentUser`.
     * Die Funktion hört auf Änderungen am Benutzerdokument in Firestore und wandelt das Dokument in ein `User`-Objekt um.
     * Sie aktualisiert die LiveData `_currentUser` mit den abgerufenen Benutzerdaten. Tritt während der Listener-Registrierung oder beim Abruf der Daten ein Fehler auf,
     * wird dieser protokolliert und die Coroutine wird mit einer Ausnahme fortgesetzt. Der Listener wird bei Abbruch der Coroutine entfernt.
     *
     * @return Unit: The function does not return a value directly. The coroutine is resumed after the user data is fetched or if an exception occurs.
     *               Die Funktion gibt keinen Wert direkt zurück. Die Coroutine wird nach dem Abrufen der Benutzerdaten oder bei einer Ausnahme fortgesetzt.
     */
    suspend fun setCurrentUser() = suspendCancellableCoroutine { continuation ->

        try {
            val listenerRegistration =
                firebasedb2.collection("User").document(_currentFirebaseUser.value?.uid!!)
                    .addSnapshotListener { currentUser, e ->
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

                        Log.i(
                            "CONNECT",
                            "Repository: setCurrentUser: currentUser: ${_currentUser.value.toString()}"
                        )
                    }

            continuation.invokeOnCancellation {
                listenerRegistration.remove()
                Log.i("Firebase", "Repository: setCurrentUser: Listener wurde entfernt.")
            }

        } catch (e: Exception) {
            Log.i("Firebase", "Repository: setCurrentUser:  Exception", e)
        }
    }

    /**
     * Retrieves the current chat room from Firebase Firestore asynchronously based on the given chat room ID and updates the internal LiveData object `_currentChatRoom`.
     * The function listens for changes to the specified chat room document in Firestore and converts the document into a `ChatRoom` object.
     * If the chat room is successfully retrieved, it is posted to the LiveData `_currentChatRoom`. If an error occurs during the listener registration
     * or data retrieval, it is logged, and the coroutine resumes with the result. The listener is removed when the coroutine is cancelled.
     *
     * ---
     *
     * Ruft asynchron den aktuellen Chatroom aus Firebase Firestore anhand der angegebenen Chatroom-ID ab und aktualisiert das interne LiveData-Objekt `_currentChatRoom`.
     * Die Funktion hört auf Änderungen am angegebenen Chatroom-Dokument in Firestore und wandelt das Dokument in ein `ChatRoom`-Objekt um.
     * Wenn der Chatroom erfolgreich abgerufen wird, wird er an das LiveData `_currentChatRoom` übergeben. Bei einem Fehler während der Listener-Registrierung
     * oder des Datenabrufs wird dieser protokolliert, und die Coroutine wird mit dem Ergebnis fortgesetzt. Der Listener wird bei Abbruch der Coroutine entfernt.
     *
     * @param chatRoomId: String - The ID of the chat room to retrieve.
     *                    Die ID des Chatrooms, der abgerufen werden soll.
     *
     * @return Unit: The function does not return a value directly. The coroutine is resumed after the chat room is fetched or if an exception occurs.
     *               Die Funktion gibt keinen Wert direkt zurück. Die Coroutine wird nach dem Abrufen des Chatrooms oder bei einer Ausnahme fortgesetzt.
     */
    suspend fun getCurrentChatRoom(chatRoomId: String) =
        suspendCancellableCoroutine { continuation ->

            try {
                val listenerRegistration = firebasedb2.collection("ChatRooms").document(chatRoomId)
                    .addSnapshotListener { it, e ->
                        val chatRoom = it!!.toObject<ChatRoom>()
                        if (chatRoom != null) {
                            chatRoom.chatRoomId = chatRoomId
                        }
                        _currentChatRoom.postValue(chatRoom)
                        if (!continuation.isCompleted) {
                            continuation.resume(Unit)
                        }
                        Log.i(
                            "CONNECT",
                            "Repository: getCurrentChatRoom: currentChatRoom: ${_currentChatRoom.value.toString()}"
                        )
                    }
                continuation.invokeOnCancellation {
                    listenerRegistration.remove()
                    Log.i("Firebase", "Repo getCurrentChatRoom: Listener wurde entfernt.")
                }

                Log.i("Firebase", "Repo: getCurrentChatRoom: ${_currentChatRoom.toString()}")

            } catch (e: Exception) {
                Log.i("Firebase", "Repo: getCurrentChatRoom: ${e.toString()}")
            }
        }

    /**
     * Sets the provided `ChatRoom` object as the current chat room by updating the internal LiveData object `_currentChatRoom`.
     * This function is used to manually update the current chat room without fetching it from a remote source.
     *
     * ---
     *
     * Setzt das übergebene `ChatRoom`-Objekt als aktuellen Chatroom, indem das interne LiveData-Objekt `_currentChatRoom` aktualisiert wird.
     * Diese Funktion wird verwendet, um den aktuellen Chatroom manuell zu aktualisieren, ohne ihn von einer externen Quelle abzurufen.
     *
     * @param chatRoom: ChatRoom - The chat room object to set as the current chat room.
     *                            Das Chatroom-Objekt, das als aktueller Chatroom gesetzt werden soll.
     *
     * @return Unit: This function does not return a value.
     *               Diese Funktion gibt keinen Wert zurück.
     */
    fun setCurrentChatroom(chatRoom: ChatRoom) {
        _currentChatRoom.value = chatRoom
    }

    /**
     * Retrieves a list of messages from the "Messages" collection in Firebase Firestore asynchronously and updates the internal LiveData object `_firebaseMessageList`.
     * The function listens for changes in the "Messages" collection and converts the documents into `Message` objects.
     * These messages are added to a list, sorted by their timestamp, and posted to `_firebaseMessageList`. If an error occurs during
     * listener registration or data retrieval, it is logged, and the coroutine resumes with an exception. The listener is removed when the coroutine is cancelled.
     *
     * ---
     *
     * Ruft asynchron eine Liste von Nachrichten aus der "Messages"-Sammlung in Firebase Firestore ab und aktualisiert das interne LiveData-Objekt `_firebaseMessageList`.
     * Die Funktion hört auf Änderungen in der "Messages"-Sammlung und wandelt die Dokumente in `Message`-Objekte um.
     * Diese Nachrichten werden in einer Liste gespeichert, nach ihrem Zeitstempel sortiert und an `_firebaseMessageList` übergeben.
     * Bei einem Fehler während der Listener-Registrierung oder des Datenabrufs wird dieser protokolliert, und die Coroutine wird mit einer Ausnahme fortgesetzt.
     * Der Listener wird bei Abbruch der Coroutine entfernt.
     *
     * @return Unit: The function does not return a value directly. The coroutine is resumed after the messages are fetched or if an exception occurs.
     *               Die Funktion gibt keinen Wert direkt zurück. Die Coroutine wird nach dem Abrufen der Nachrichten oder bei einer Ausnahme fortgesetzt.
     */
    suspend fun getMessageList() = suspendCancellableCoroutine<Unit> { continuation ->
        val firebaseCurrentMessageList: MutableList<Message> = mutableListOf()

        try {

            val listenerRegistration = firebaseDB.collection("Messages")
                .addSnapshotListener { messageList, e ->
                    if (e != null) {
                        Log.i(
                            "Firebase",
                            "Repo getRealtimeFirebaseDataCurrentMessageList Listen failed.",
                            e
                        )
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
                    val firebaseCurrentMessageList2 =
                        firebaseCurrentMessageList.sortedBy { it.timestamp }

                    _firebaseMessageList.postValue(firebaseCurrentMessageList2)

                    if (!continuation.isCompleted) {
                        continuation.resume(Unit)
                    }

                    Log.i(
                        "Firebase",
                        "Repo getRealtimeFirebaseDataCurrentMessageList: ${firebaseCurrentMessageList2.toString()}"
                    )
                }

            continuation.invokeOnCancellation {
                listenerRegistration.remove()
                Log.i(
                    "Firebase",
                    "Repo getRealtimeFirebaseDataCurrentMessageList: Listener wurde entfernt."
                )
            }

        } catch (e: Exception) {
            Log.i("Firebase", "Repo: getRealtimeFirebaseDataCurrentMessageList: ${e.toString()}")
            if (!continuation.isCompleted) {
                continuation.resumeWithException(e)
            }
        }
    }

    /**
     * Retrieves a list of chat rooms from Firebase Firestore asynchronously and updates the internal LiveData object `_firebaseChatRoomList`.
     * The function listens for changes in the "ChatRooms" collection where the current user is a participant and converts the documents
     * into `ChatRoom` objects. The list of chat rooms is then posted to `_firebaseChatRoomList`. If an error occurs during the listener registration
     * or data retrieval, it is logged, and the coroutine resumes with an exception. The listener is removed when the coroutine is cancelled.
     *
     * ---
     *
     * Ruft asynchron eine Liste von Chatrooms aus Firebase Firestore ab und aktualisiert das interne LiveData-Objekt `_firebaseChatRoomList`.
     * Die Funktion hört auf Änderungen in der "ChatRooms"-Sammlung, in der der aktuelle Benutzer als Teilnehmer aufgeführt ist, und wandelt die Dokumente
     * in `ChatRoom`-Objekte um. Die Liste der Chatrooms wird dann an `_firebaseChatRoomList` übergeben. Tritt ein Fehler während der Listener-Registrierung
     * oder des Datenabrufs auf, wird dieser protokolliert und die Coroutine wird mit einer Ausnahme fortgesetzt. Der Listener wird bei Abbruch der Coroutine entfernt.
     *
     * @return Unit: The function does not return a value directly. The coroutine is resumed after the chat rooms are fetched or if an exception occurs.
     *               Die Funktion gibt keinen Wert direkt zurück. Die Coroutine wird nach dem Abrufen der Chatrooms oder bei einer Ausnahme fortgesetzt.
     */
    suspend fun getChatRoomList() = suspendCancellableCoroutine<Unit> { continuation ->
        var isResumed = false // Flag, um mehrfaches Fortsetzen zu verhindern

        val listenerRegistration = firebaseDB.collection("ChatRooms")
            .whereArrayContains("chatParticipants", _currentFirebaseUser.value!!.uid)
            .addSnapshotListener { chatRoomList, e ->
                if (e != null) {
                    Log.i("Firebase", "Repo: getChatRoomList: Listen failed: $e")
                    if (!isResumed) {
                        continuation.resumeWithException(e)
                        isResumed = true
                    }
                    return@addSnapshotListener
                }

                val chatRoomList2: MutableList<ChatRoom> = mutableListOf()
                _firebaseChatRoomList.value = mutableListOf()
                if (chatRoomList != null) {
                    for (chatRoom in chatRoomList) {
                        val chatRoom2 = chatRoom.toObject<ChatRoom>()
                        Log.i("Firebase", "Repo: getChatRoomList: Listen passed: ${chatRoom.data}")
                        chatRoomList2.add(chatRoom2)
                    }
                    Log.i(
                        "Firebase",
                        "Repo: getChatRoomList: Listen passed: List: $chatRoomList2"
                    )
                    _firebaseChatRoomList.value = chatRoomList2
                } else {
                    Log.i("Firebase", "Repo: getChatRoomList: No chatRooms found.")
                }

                if (!isResumed) {
                    continuation.resume(Unit)
                    isResumed = true
                }
            }

        continuation.invokeOnCancellation {
            listenerRegistration.remove()
        }
    }

    /**
     * Retrieves a list of leave requests from Firebase Firestore asynchronously and updates the internal LiveData object `_firebaseLeaveRequestList`.
     * The function listens for changes in the "LeaveRequest" collection where the current user is the requester and converts the documents
     * into `LeaveRequest` objects. These leave requests are added to a list, which is then posted to `_firebaseLeaveRequestList`.
     * If an error occurs during the listener registration or data retrieval, it is logged and the coroutine resumes with an exception.
     * The listener is removed when the coroutine is cancelled.
     *
     * ---
     *
     * Ruft asynchron eine Liste von Urlaubsanträgen aus Firebase Firestore ab und aktualisiert das interne LiveData-Objekt `_firebaseLeaveRequestList`.
     * Die Funktion hört auf Änderungen in der "LeaveRequest"-Sammlung, in der der aktuelle Benutzer der Antragsteller ist, und wandelt die Dokumente
     * in `LeaveRequest`-Objekte um. Diese Urlaubsanträge werden in einer Liste gespeichert und an `_firebaseLeaveRequestList` übergeben.
     * Bei einem Fehler während der Listener-Registrierung oder des Datenabrufs wird dieser protokolliert, und die Coroutine wird mit einer Ausnahme fortgesetzt.
     * Der Listener wird bei Abbruch der Coroutine entfernt.
     *
     * @return Unit: The function does not return a value directly. The coroutine is resumed after the leave requests are fetched or if an exception occurs.
     *               Die Funktion gibt keinen Wert direkt zurück. Die Coroutine wird nach dem Abrufen der Urlaubsanträge oder bei einer Ausnahme fortgesetzt.
     */
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

                    currentFirebaseLeaveRequestList.clear()
                    for (leaveRequest in leaveRequestList!!) {
                        Log.d(
                            "Firebase",
                            "Repo: leaveRequest: ${leaveRequest.id} => ${leaveRequest.data}"
                        )

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

                    if (!continuation.isCompleted) {
                        continuation.resume(Unit)
                    }

                    Log.i(
                        "Firebase",
                        "Repo getFirebaseDataLeaveRequests: ${currentFirebaseLeaveRequestList.toString()}"
                    )
                }

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

    /**
     * Retrieves a list of attachments from Firebase Firestore asynchronously and updates the internal LiveData object `_firebaseAttachmentList`.
     * The function listens for changes in the "Attachments" collection and converts the documents into `Attachment` objects.
     * These attachments are added to a list, sorted by their timestamp, and posted to `_firebaseAttachmentList`.
     * If an error occurs during the listener registration or data retrieval, it is logged, and the coroutine resumes with an exception.
     * The listener is removed when the coroutine is cancelled.
     *
     * ---
     *
     * Ruft asynchron eine Liste von Anhängen aus Firebase Firestore ab und aktualisiert das interne LiveData-Objekt `_firebaseAttachmentList`.
     * Die Funktion hört auf Änderungen in der "Attachments"-Sammlung und wandelt die Dokumente in `Attachment`-Objekte um.
     * Diese Anhänge werden in einer Liste gespeichert, nach ihrem Zeitstempel sortiert und an `_firebaseAttachmentList` übergeben.
     * Bei einem Fehler während der Listener-Registrierung oder des Datenabrufs wird dieser protokolliert, und die Coroutine wird mit einer Ausnahme fortgesetzt.
     * Der Listener wird bei Abbruch der Coroutine entfernt.
     *
     * @return Unit: The function does not return a value directly. The coroutine is resumed after the attachments are fetched or if an exception occurs.
     *               Die Funktion gibt keinen Wert direkt zurück. Die Coroutine wird nach dem Abrufen der Anhänge oder bei einer Ausnahme fortgesetzt.
     */
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
                        Log.d(
                            "Firebase",
                            "Repo: Attachment: ${attachment.id} => ${attachment.data}"
                        )

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
                    val firebaseCurrentAttachmentList2 =
                        firebaseCurrentAttachmentList.sortedBy { it.timestamp }

                    _firebaseAttachmentList.postValue(firebaseCurrentAttachmentList2)

                    if (!continuation.isCompleted) {
                        continuation.resume(Unit)
                    }

                    Log.i(
                        "Firebase",
                        "Repo getFirebaseAttachments: ${firebaseCurrentAttachmentList2.toString()}"
                    )
                }

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

    /**
     * Retrieves a list of holidays from a specific subcollection in Firebase Firestore asynchronously and updates the internal LiveData object `_firebaseHolidayList`.
     * The function listens for changes in the "HolidayList" subcollection of the current user's document and converts the documents into `Holiday` objects.
     * These holidays are added to a list and posted to `_firebaseHolidayList`.
     * If an error occurs during the listener registration or data retrieval, it is logged, and the coroutine resumes with an exception.
     * The listener is removed when the coroutine is cancelled.
     *
     * ---
     *
     * Ruft asynchron eine Liste von Feiertagen aus einer bestimmten Unterkollektion in Firebase Firestore ab und aktualisiert das interne LiveData-Objekt `_firebaseHolidayList`.
     * Die Funktion hört auf Änderungen in der Unterkollektion "HolidayList" des Dokuments des aktuellen Benutzers und wandelt die Dokumente in `Holiday`-Objekte um.
     * Diese Feiertage werden in einer Liste gespeichert und an `_firebaseHolidayList` übergeben.
     * Bei einem Fehler während der Listener-Registrierung oder des Datenabrufs wird dieser protokolliert, und die Coroutine wird mit einer Ausnahme fortgesetzt.
     * Der Listener wird bei Abbruch der Coroutine entfernt.
     *
     * @return Unit: The function does not return a value directly. The coroutine is resumed after the holidays are fetched or if an exception occurs.
     *               Die Funktion gibt keinen Wert direkt zurück. Die Coroutine wird nach dem Abrufen der Feiertage oder bei einer Ausnahme fortgesetzt.
     */
    suspend fun getFirebaseHolidaylist() = suspendCancellableCoroutine<Unit> { continuation ->
        val firebaseHolidayList2: MutableList<Holiday> = mutableListOf()

        try {

            val listenerRegistration =
                firebaseDB.collection("User/${_currentFirebaseUser.value!!.uid}/HolidayList")
                    .addSnapshotListener { holidayList2, e ->
                        if (e != null) {
                            Log.i("Firebase", "Repo getFirebaseHolidaylist Listen failed.", e)
                            if (!continuation.isCompleted) {
                                continuation.resumeWithException(e)
                            }
                            return@addSnapshotListener
                        }

                        firebaseHolidayList2.clear()
                        holidayList.clear()
                        for (holiday in holidayList2!!) {
                            Log.d("Firebase", "Repo: Holiday: ${holiday.id} => ${holiday.data}")


                            val holiday2: Holiday = Holiday(
                                holidayId = holiday.id,
                                holidayName = holiday.getString("holidayName"),
                                holidayRegion = holiday.getString("holidayRegion"),
                                holidayDay = holiday.getLong("holidayDay")?.toInt(),
                                holidayMonth = holiday.getLong("holidayMonth")?.toInt(),
                                holidayYear = holiday.getLong("holidayYear")?.toInt(),
                                comment = holiday.getString("comment"),
                                augsburg = holiday.getBoolean("augsburg"),
                                katholisch = holiday.getBoolean("katholisch")
                            )

                            firebaseHolidayList2.add(holiday2)
                            holidayList.add(holiday2)
                        }

                        _firebaseHolidayList.postValue(firebaseHolidayList2)

                        if (holidayList.isEmpty()) {

                        }

                        if (!continuation.isCompleted) {
                            continuation.resume(Unit)
                        }

                        Log.i(
                            "Firebase",
                            "Repo getFirebaseHolidaylist: ${firebaseHolidayList2.toString()}"
                        )
                    }

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

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------/FIREBASE-------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
}