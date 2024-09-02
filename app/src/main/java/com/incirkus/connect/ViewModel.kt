package com.incirkus.connect

import android.app.Application
import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.incirkus.connect.DATA.Model.APIResponse
import com.incirkus.connect.DATA.Model.Day
import com.incirkus.connect.DATA.Model.Holiday
import com.incirkus.connect.DATA.Model.Message
import com.incirkus.connect.DATA.Model.Month
import com.incirkus.connect.DATA.Model.User
import com.incirkus.connect.DATA.Repository
//import com.incirkus.connect.DATA.local.getDataBase
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.incirkus.connect.DATA.Model.Attachment
import com.incirkus.connect.DATA.Model.ChatRoom
import com.incirkus.connect.DATA.Model.LeaveRequest

import kotlinx.coroutines.launch
import java.io.File
import java.time.Instant

import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.IsoFields
import java.util.Locale
import java.util.UUID


class ViewModel(application: Application) : AndroidViewModel(application) {

    //private val database = getDataBase(application)
    val repository = Repository()
    //val repository = Repository(database,this)



    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------Calendar Items-------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    val monthList = createMonthList()
    val actualMonth = getactualMonth()

    private var _currentMonth = MutableLiveData<Month>(actualMonth)
    val currentMonth: LiveData<Month> = _currentMonth

    private fun createMonthList(): List<Month>{

        val monthList: MutableList<Month> = mutableListOf()

        val currentDate = LocalDate.now()

        val startDate = currentDate.minusMonths(12)
        val endDate = currentDate.plusMonths(24)

        var yearMonth = YearMonth.from(startDate)

        while (yearMonth <= YearMonth.from(endDate)){

            val year = yearMonth.year
            val monthNumber = yearMonth.month.value
            val monthString = yearMonth.month.getDisplayName(TextStyle.FULL,Locale.getDefault())
            val monthLength = yearMonth.lengthOfMonth()
            val firstDayOfMonth = LocalDate.of(year, monthNumber, 1)
            val firstDayOfMontAsWeekdayString = firstDayOfMonth.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
            val firstDayOfMontAsWeekdayInt = firstDayOfMonth.dayOfWeek.value


            val daylist: List<Day> = createDayList(year,monthNumber,monthLength)


            val month = Month(
                year = year,
                monthNumber = monthNumber,
                monthString = monthString,
                monthLength = monthLength,
                firstDayOfMontAsWeekdayString = firstDayOfMontAsWeekdayString,
                firstDayOfMontAsWeekdayInt = firstDayOfMontAsWeekdayInt,
                daylist = daylist
            )

            monthList.add(month)

            yearMonth = yearMonth.plusMonths(1)
        }

        return monthList

    }


    fun createDayList(year: Int, monthNumber: Int, monthLength:Int): List<Day>{

        val dayList: MutableList<Day> = mutableListOf()

        when (LocalDate.of(year, monthNumber,1).dayOfWeek.value){
            1 ->{
                fillDayList (year,monthNumber,monthLength, dayList)
            }
            2 ->{
                addPlaceholderDaysToDayList(dayList)
                fillDayList (year,monthNumber,monthLength, dayList)
            }
            3 ->{
                repeat(2){
                    addPlaceholderDaysToDayList(dayList)
                }
                fillDayList (year,monthNumber,monthLength, dayList)
            }
            4 ->{
                repeat(3){
                    addPlaceholderDaysToDayList(dayList)
                }
                fillDayList (year,monthNumber,monthLength, dayList)
            }
            5 ->{
                repeat(4){
                    addPlaceholderDaysToDayList(dayList)
                }
                fillDayList (year,monthNumber,monthLength, dayList)
            }
            6 ->{
                repeat(5){
                    addPlaceholderDaysToDayList(dayList)
                }
                fillDayList (year,monthNumber,monthLength, dayList)
            }
            7 ->{
                repeat(6){
                    addPlaceholderDaysToDayList(dayList)
                }
                fillDayList (year,monthNumber,monthLength, dayList)
            }
        }



        return dayList

    }

    private fun fillDayList(year: Int, monthNumber: Int, monthLength:Int, dayList: MutableList<Day>) {

        for (day in 1..monthLength) {
            val date = LocalDate.of(year, monthNumber, day)
            val weekdayAsString = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
            val weekdayAsInt = date.dayOfWeek.value
            val calendarweek = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)



            val day2 = Day(
                day = day,
                month = monthNumber,
                year = year,
                weekdayAsString = weekdayAsString,
                weekdayAsInt = weekdayAsInt,
                calendarweek = calendarweek
            )

            dayList.add(day2)

        }
    }

    private fun addPlaceholderDaysToDayList(dayList: MutableList<Day>){

        val placeholderDay: Day = Day(
            day = 0,
            month = 0,
            year = 0,
            weekdayAsString = "Placeholder",
            weekdayAsInt = 0,
            calendarweek = 0
        )

        dayList.add(placeholderDay)
    }


    fun getactualMonth(): Month{

        val currentDate = LocalDate.now()

        var yearMonth = YearMonth.from(currentDate)

        val year = yearMonth.year
        val monthNumber = yearMonth.month.value
        val monthString = yearMonth.month.getDisplayName(TextStyle.FULL,Locale.getDefault())
        val monthLength = yearMonth.lengthOfMonth()
        val firstDayOfMonth = LocalDate.of(year, monthNumber, 1)
        val firstDayOfMontAsWeekdayString = firstDayOfMonth.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        val firstDayOfMontAsWeekdayInt = firstDayOfMonth.dayOfWeek.value

        val daylist: List<Day> = createDayList(year,monthNumber,monthLength)

        val month = Month(
            year = year,
            monthNumber = monthNumber,
            monthString = monthString,
            monthLength = monthLength,
            firstDayOfMontAsWeekdayString = firstDayOfMontAsWeekdayString,
            firstDayOfMontAsWeekdayInt =firstDayOfMontAsWeekdayInt,
            daylist = daylist
        )

        return month

    }

    fun monthMinus1(month: Month){

        if (month.monthNumber != 1){
            month.monthNumber = month.monthNumber -1
            changeMonthString(month)
        } else{
            month.monthNumber = 12
            month.year = month.year -1
            changeMonthString(month)
        }

        _currentMonth.postValue(month)
    }

    fun monthPlus1(month: Month){

        if (month.monthNumber != 12){
            month.monthNumber = month.monthNumber +1
            changeMonthString(month)
        } else{
            month.monthNumber = 1
            month.year = month.year +1
            changeMonthString(month)
        }

        _currentMonth.postValue(month)
    }

    fun yearMinus1(month: Month){
        month.year = month.year-1
        _currentMonth.postValue(month)
    }

    fun yearPlus1(month: Month){
        month.year = month.year+1
        _currentMonth.postValue(month)
    }

    private fun changeMonthString(month: Month){

        when(month.monthNumber){
            1 -> {
                month.monthString = "January"
            }
            2 -> {
                month.monthString = "February"
            }
            3 -> {
                month.monthString = "March"
            }
            4 -> {
                month.monthString = "April"
            }
            5 -> {
                month.monthString = "May"
            }
            6 -> {
                month.monthString = "June"
            }
            7 -> {
                month.monthString = "July"
            }
            8 -> {
                month.monthString = "August"
            }
            9 -> {
                month.monthString = "September"
            }
            10 -> {
                month.monthString = "October"
            }
            11 -> {
                month.monthString = "November"
            }
            12 -> {
                month.monthString = "December"
            }
        }
    }

    fun changeCurrentMonth(month: Month){
        _currentMonth.postValue(month)
    }


//-----------------------------------------------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------API----------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------------------------------------

    val firebaseHolidayList = repository.firebaseHolidayList
    val holidayList: MutableList<Holiday> = repository.holidayList

    fun getListForHolidays(){
        if (holidayList.isEmpty()){
        viewModelScope.launch {
            var responseList = repository.getHolidayList()
            convertResponseToHolidayList(responseList)
        }
        }
    }

    fun getListForHolidaysManual(){
            viewModelScope.launch {
                var responseList = repository.getHolidayList()
                deleteHolidayList(responseList)
                //convertResponseToHolidayList(responseList)
            }
    }

    fun getHolidayListForSomeStates(states: String){

            viewModelScope.launch {
                var responseList = repository.getHolidayListForSomeStates(states)
                deleteHolidayList(responseList)
                //convertResponseToHolidayList(responseList)
            }

    }

    fun deleteHolidayList(responseList: APIResponse){
        val holidayListRef = firedatabase.collection("User").document(currentUser.value!!.userId).collection("HolidayList")

// Abrufen aller Dokumente in der HolidayList-Unterkollektion
        holidayListRef.get().addOnSuccessListener { querySnapshot ->
            val deleteTasks = mutableListOf<Task<Void>>()
            // Für jedes Dokument in der HolidayList-Unterkollektion
            if (!querySnapshot.isEmpty){
                for (document in querySnapshot) {
                    // Lösche das Dokument
                    holidayListRef.document(document.id).delete()
                }
            }
            Tasks.whenAll(deleteTasks).addOnSuccessListener {
                convertResponseToHolidayList(responseList)
            }

        }.addOnFailureListener { exception ->
            // Fehlerbehandlung
            Log.e("Firestore", "Error deleting documents: ", exception)
        }
    }

    fun convertResponseToHolidayList(responseList: APIResponse){



        if (responseList != null){

            for (holiday in responseList.feiertage){
                val name: String = holiday.fname
                val date: String = holiday.date

                var year: Int = date.split("-")[0].toInt()
                var month: Int = date.split("-")[1].toInt()
                var day: Int = date.split("-")[2].toInt()


                var region = ""
                if (holiday.all_states == "1"){
                    region = "DE"
                }else{
                    if (holiday.bw == "1"){
                        region = region + "bw,"
                    }
                    if (holiday.by == "1"){
                        region = region + "by,"
                    }
                    if (holiday.be == "1"){
                        region = region + "be,"
                    }
                    if (holiday.bb == "1"){
                        region = region + "bb,"
                    }
                    if (holiday.hb == "1"){
                        region = region + "hb,"
                    }
                    if (holiday.hh == "1"){
                        region = region + "hh,"
                    }
                    if (holiday.he == "1"){
                        region = region + "he,"
                    }
                    if (holiday.mv == "1"){
                        region = region + "mv,"
                    }
                    if (holiday.ni == "1"){
                        region = region + "ni,"
                    }
                    if (holiday.nw == "1"){
                        region = region + "nw,"
                    }
                    if (holiday.rp == "1"){
                        region = region + "rp,"
                    }
                    if (holiday.sl == "1"){
                        region = region + "sl,"
                    }
                    if (holiday.sn == "1"){
                        region = region + "sn,"
                    }
                    if (holiday.st == "1"){
                        region = region + "st,"
                    }
                    if (holiday.sh == "1"){
                        region = region + "sh,"
                    }
                    if (holiday.th == "1"){
                        region = region + "th,"
                    }

                }
                val comment: String = holiday.comment
                var augsburg: Boolean = false
                var katholisch: Boolean = false

                if (holiday.augsburg != null){
                    augsburg = true
                }

                if (holiday.katholisch != null){
                    katholisch = true
                }

                val holiday: Holiday = Holiday(

                    holidayId= UUID.randomUUID().toString(),
                    holidayName = name,
                    holidayRegion = region,
                    holidayDay = day,
                    holidayMonth = month,
                    holidayYear = year,
                    comment= comment,
                    augsburg = augsburg,
                    katholisch = katholisch
                )




                firedatabase.collection("User")
                    .document(currentUser.value!!.userId)
                    .collection("HolidayList")
                    .document(holiday.holidayId)
                    .set(holiday)
                holidayList.add(holiday)
            }

        }

    }



    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------FIREBASE-------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()
    private val firedatabase = FirebaseFirestore.getInstance()

    val firebaseUserList: LiveData<List<User>> = repository.firebaseUserList
    val currentFirebaseUser: LiveData<FirebaseUser?> = repository.currentFirebaseUser
    val currentUser = repository.currentUser
    val firebaseChatRoomList = repository.firebaseChatRoomList
    val currentChatRoom = repository.currentChatRoom
    val firebaseMessageList = repository.firebaseMessageList
    var currentChatPartner = User()
    val firebaseLeaveRequestList = repository.firebaseLeaveRequestList
    val firebaseAttachmentList = repository.firebaseAttachmentList

    var _currentChatPartner = MutableLiveData<User>()
    val currentChatPartner2: LiveData<User> = _currentChatPartner







    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------FIREBASE-STORAGE---------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    val storage: FirebaseStorage = Firebase.storage

    // Create a storage reference from our app
    var storageRef = storage.reference

    val fileName : String = currentUser.value?.userId + "_profilePicture.jpg"
    var imagesRef: StorageReference? = storageRef.child("profilePictures/$fileName")
    var spaceRef = storageRef.child(fileName)

    lateinit var attachmentRef: DocumentReference
    lateinit var profileRef: DocumentReference

    fun uploadImage(uri: Uri){
        val fileName : String = currentUser.value?.userId + "_profilePicture.jpg"
        val imageRef = firebaseStorage.reference.child("images/${currentUser.value?.userId}/$fileName")
        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnCompleteListener{
            imageRef.downloadUrl.addOnCompleteListener {
                if(it.isSuccessful){
                    currentUser.value?.image = it.result.toString()
                    updateCurrentUser()
                }
            }
        }
    }


    fun updateCurrentUser(){
        val currentUserRef = currentUser.value?.let {
            firedatabase.collection("User").document(
                it.userId)
        }
        if (currentUserRef != null) {
            currentUserRef
                .update("image", currentUser.value?.image)
                .addOnSuccessListener { Log.i("Firebase", "ViewModel: updateCurrentUser: ${currentUser.value?.image}") }
                .addOnFailureListener { e -> Log.i("Firebase", "ViewModel: updateCurrentUser: ${e.toString()}") }

        }

    }

    private fun setImage(uri: Uri){
        profileRef.update("image",uri.toString())
    }



    fun uploadAttachment(uri: Uri, attachment: Attachment){
        val chatRoomId = attachment.chatRoomId
        val filename = attachment.attachmentName
        val storageRef = firebaseStorage.reference.child("Attachment/$chatRoomId/$filename")
        val uploadTask = storageRef.putFile(uri)

        uploadTask.addOnCompleteListener{
            storageRef.downloadUrl.addOnCompleteListener {
                if(it.isSuccessful){
                    attachment.path = it.result.toString()
                    sendAttachment(attachment)
                }
            }
        }
    }

    fun sendAttachment(attachment: Attachment){

        firedatabase.collection("Attachments").document(attachment.attachmentId).set(attachment)

        val message = Message(
            messageId = attachment.attachmentId,
            chatRoomId = attachment.chatRoomId,
            senderId = attachment.senderID,
            messageText = "Attachment Send: \n${attachment.attachmentName}",
            timestamp = attachment.timestamp,
            messageStatus = "send"
        )

        sendMessage(message)

        currentChatRoom.value!!.lastMessageSenderId = attachment.senderID
        currentChatRoom.value!!.lastMessage = attachment.attachmentName
        currentChatRoom.value!!.lastActivityTimestamp = attachment.timestamp
        updateChatRoom()

    }






    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------NEW APP--------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------




    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                viewModelScope.launch {
                    repository.setCurrentFirebaseUser(it.result.user)
                    loadData()
                }
            } else {
                //TODO Pop-Up Message das Login fehlgeschlagen
            }
        }
    }

    fun logout() {
        firebaseAuth.signOut()
        viewModelScope.launch {
            repository.logout()
            repository.clearMessagelist()
        }
    }


    fun createChatroom(user:User):Boolean{

        var checkIfChatRoomExists: Boolean = false
        if (!firebaseChatRoomList.value.isNullOrEmpty()){

            for (chatRoom in firebaseChatRoomList.value!!){
                if (chatRoom.chatParticipants.containsAll(listOf(currentUser.value?.userId!!, user.userId))){
                    checkIfChatRoomExists = true
                    getCurrentChatRoom(chatRoom.chatRoomId)
                    setCurrentChatroom(chatRoom)
                    getChatPartner(user.userId)
                }
            }
        }
        //TODO: Abfrage ob dieser Chatroom bereits existiert

        if (!checkIfChatRoomExists){

        val chatRoomId = UUID.randomUUID().toString()
        val chatRoomName :String = currentUser.value?.firstName!! + user.firstName
        val lastMessage: String = ""
        var lastActivityTimestamp: Long = 0L
        var chatParticipants: MutableList<String?> = mutableListOf(currentUser.value?.userId!!, user.userId)

        val chatroom: ChatRoom = ChatRoom(
            chatRoomId = chatRoomId,
            chatRoomName = chatRoomName,
            lastMessage = lastMessage,
            lastActivityTimestamp = lastActivityTimestamp,
            chatParticipants = chatParticipants
        )

        firedatabase.collection("ChatRooms").document(chatRoomId).set(chatroom)

        viewModelScope.launch {
            repository.getFirebaseDataChatRooms()
        }

        getCurrentChatRoom(chatRoomId)

            setCurrentChatroom(chatroom)
            getChatPartner(user.userId)
        }
        return checkIfChatRoomExists
    }

    fun getCurrentChatRoom(chatRoomId: String) {
        if (currentFirebaseUser != null) {
            viewModelScope.launch {
                repository.getCurrentChatRoom(chatRoomId)
                //repository.getFirebaseDataCurrentMessageList()
            }
        }
    }

    fun setCurrentChatroom(chatRoom: ChatRoom){
        viewModelScope.launch {
            repository.setCurrentChatroom(chatRoom)
            //repository.getFirebaseDataCurrentMessageList()
            val chatPartnerID = getChatPartnerID(chatRoom)
            if (chatPartnerID != null){
                getChatPartner(chatPartnerID)
            }
        }
    }




    fun sendMessage(message: Message){

        firedatabase.collection("Messages").document(message.messageId).set(message)

        viewModelScope.launch {
            //TODO MessageList aktualisieren
//            repository.getFirebaseDataChatRooms()
//            repository.getFirebaseDataCurrentMessageList()
        }
    }

    fun updateChatRoom(){
        val chatRoomRef = currentChatRoom.value?.let {
            firedatabase.collection("ChatRooms").document(
                it.chatRoomId)
        }
        if (chatRoomRef != null) {
            chatRoomRef
                .update("lastMessage", currentChatRoom.value?.lastMessage)
                .addOnSuccessListener { Log.i("Firebase", "ViewModel: updateChatRoom: ${currentChatRoom.value?.lastMessage}") }
                .addOnFailureListener { e -> Log.i("Firebase", "ViewModel: updateChatRoom: ${e.toString()}") }
            chatRoomRef
                .update("lastActivityTimestamp", currentChatRoom.value?.lastActivityTimestamp)
                .addOnSuccessListener { Log.i("Firebase", "ViewModel: updateChatRoom: ${currentChatRoom.value?.lastActivityTimestamp}") }
                .addOnFailureListener { e -> Log.i("Firebase", "ViewModel: updateChatRoom: ${e.toString()}") }
            chatRoomRef
                .update("lastMessageSenderId", currentChatRoom.value?.lastMessageSenderId)
                .addOnSuccessListener { Log.i("Firebase", "ViewModel: updateChatRoom: ${currentChatRoom.value?.lastMessageSenderId}") }
                .addOnFailureListener { e -> Log.i("Firebase", "ViewModel: updateChatRoom: ${e.toString()}") }
        }

    }

    fun getChatPartnerID(chatRoom: ChatRoom): String?{
        var chatPartnerID: String? = ""

        for (id in chatRoom.chatParticipants){
            if (id != currentUser.value?.userId){
                chatPartnerID = id
            }
        }
        return chatPartnerID
    }

    fun getChatPartner(id: String): User{

        var chatPartner: User = User()

        for (oneUser in firebaseUserList.value!!){
            if (oneUser.userId == id)
                chatPartner = oneUser
        }
        _currentChatPartner.value = chatPartner
        currentChatPartner = chatPartner
        return chatPartner
    }

    fun convertTimestampToDate(timestamp:Long):String{
        val instant = Instant.ofEpochMilli(timestamp)
        val zoneId = ZoneId.systemDefault()
        val zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId)

        val today = LocalDate.now(zoneId)
        val messageDate = zonedDateTime.toLocalDate()

        return if (messageDate.isEqual(today)) {
            // Zeige nur die Uhrzeit an, wenn das Datum heute ist
            val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
            zonedDateTime.format(timeFormatter)
        } else {
            // Zeige Datum und Uhrzeit an, wenn das Datum nicht heute ist
            val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
            zonedDateTime.format(dateTimeFormatter)
        }
    }

    fun convertTimestampToDates(timestamp:Long):String{
        val instant = Instant.ofEpochMilli(timestamp)
        val zoneId = ZoneId.systemDefault()
        val zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId)

        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        return zonedDateTime.format(dateTimeFormatter)
    }

    fun clearMessagelist(){
        repository.clearMessagelist()
    }

    fun loadData() {
        viewModelScope.launch {
            repository.getFirebaseDataUser()
            repository.setCurrentUser()
            repository.getChatRoomList()
            repository.getMessageList()
            repository.getFirebaseDataLeaveRequests()
            repository.getFirebaseAttachments()
            repository.getFirebaseHolidaylist()
        }
    }



    var userSearchList: LiveData<List<User>> = repository.firebaseUserList
    private val _filteredUserList = MutableLiveData<List<User>>()
    val filteredUserList: LiveData<List<User>> = _filteredUserList

    fun searchFilter(filterText: String){
        if (!userSearchList.value.isNullOrEmpty()) {
//            Log.i("Firebase", "ViewModel: searchFilter: ${userSearchList.value}")
//            var _searchlist = MutableLiveData<List<User>>(userSearchList.value)
//            val searchlist: LiveData<List<User>> = _searchlist
//            _searchlist.value = _searchlist.value!!.filter { it.fullName!!.contains(filterText,true)  }
//            userSearchList = searchlist
//        }
            val currentList = userSearchList.value ?: return
            if (filterText.isEmpty()) {
                _filteredUserList.value = currentList
            } else {
                _filteredUserList.value = currentList.filter {
                    it.fullName!!.contains(filterText, true) || it.department!!.contains(filterText,true)  // oder andere Kriterien
                }
            }
        }
    }

    fun submitLeaveRequest(leaveRequest: LeaveRequest){

        firedatabase.collection("LeaveRequest").document(leaveRequest.requestId).set(leaveRequest)

    }


    fun forgotPassword(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {



            } else {
                //TODO Pop-Up Message das Login fehlgeschlagen
            }
        }
    }

    fun changePassword(email: String, passwordOld: String, passwordNew: String) {

        val user = Firebase.auth.currentUser!!

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
        user.let {

        val credential = EmailAuthProvider.getCredential(email, passwordOld)

// Prompt the user to re-provide their sign-in credentials
        it.reauthenticate(credential)
            .addOnCompleteListener {reauthentication->
                Log.d("Firebase", "User re-authenticated.")
                if (reauthentication.isSuccessful){

                it.updatePassword(passwordNew)
                    .addOnCompleteListener { passwordUpdate ->
                        if (passwordUpdate.isSuccessful) {
                            Log.d("Firebase", "User password updated.")
                        }
                    }
                }
            }
        }


    }

}
