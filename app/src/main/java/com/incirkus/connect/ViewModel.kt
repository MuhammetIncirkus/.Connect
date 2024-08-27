package com.incirkus.connect

import android.app.Application
import android.net.Uri
import android.util.Log
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
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.incirkus.connect.DATA.Model.ChatRoom

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

/*

    val currentUserOld = repository.currentUser
    val userList = repository.userList
    val currentChatRoom = repository.currentChatRoom
    val currentChatParticipants = repository.currentChatParticipants
    val usersChatRoomIdList = repository.usersChatRoomIdList
    val usersChatRoomList = repository.usersChatRoomList


//    fun preloadItems(){
//        viewModelScope.launch {
//            repository.preload()
//            repository.loadCurrentUserFromUserList()
//            repository.loadCurrentUser()
//            //repository.loadUserList()
//        }
//    }

//    fun loadCurrentUser(): User{
//        lateinit var user: User
//        viewModelScope.launch {
//            user = repository.loadCurrentUserFromUserlist()
//        }
//        return user
//    }

//    fun loadCurrentUser2(){
//        viewModelScope.launch {
//            repository.loadCurrentUser()
//        }
//    }

//    fun loadUserList(){
//        viewModelScope.launch {
//            repository.loadUserList()
//        }
//    }

//    fun createNewChatroom(currentUser: User, selectedUser: User){
//        viewModelScope.launch {
//            repository.createNewChatroom(currentUser,selectedUser)
//        }
//    }

//    fun createNewChatroom2(chatRoom: ChatRoom){
//        viewModelScope.launch {
//            repository.createNewChatroom2(chatRoom)
//        }
//    }

//    fun newUser(user:User){
//        viewModelScope.launch {
//            repository.newUser(user)
//        }
//    }

//    fun getChatRoom(chatRoomId:Long){
//        viewModelScope.launch {
//            repository.getChatRoom(chatRoomId)
//        }
//    }
//
//    fun getChatParticipants(chatParticipantsId:Long){
//        viewModelScope.launch {
//            repository.getChatParticipants(chatParticipantsId)
//        }
//    }
//
//    fun sendMessage(message: Message){
//        viewModelScope.launch {
//            try {
//
//                repository.sendMessage(message)
//            }catch (e:Exception){
//                Log.e("ConnectTag", "ViewModel.sendMessage: Error sending message: ${e.message}")
//            }
//        }
//    }

//    fun loadUsersChatLists(){
//        viewModelScope.launch {
//            repository.loadUsersChatParticipantsLists()
//        }
//    }
//
//    fun getOneUserById(userID: Long): LiveData<User>{
//        lateinit var user: LiveData<User>
//        viewModelScope.launch {
//            user = repository.getOneUserById(userID)
//        }
//        return user
//    }*/

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

    var holidayList: MutableList<Holiday> = mutableListOf()

    fun getListForHolidays(){

        viewModelScope.launch {
            var responseList = repository.getHolidayList()
            convertResponseToHolidayList(responseList)
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

                    holidayName = name,
                    holidayRegion = region,
                    holidayDay = day,
                    holidayMonth = month,
                    holidayYear = year,
                    comment= comment,
                    augsburg = augsburg,
                    katholisch = katholisch
                )

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


//    private val firebaseAuth = FirebaseAuth.getInstance()
//    private val firebaseStorage = FirebaseStorage.getInstance()
//    private val firedatabase = FirebaseFirestore.getInstance()
//
//
//    private var _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
//    val currentUser: LiveData<FirebaseUser?> = _currentUser
////    private var _loggedInUser = MutableLiveData<User?>()
////    val loggedInUser: LiveData<User?> = _loggedInUser
//
//    lateinit var loggedInUser: MutableLiveData<User?>
//    private var _firebaseUserList = MutableLiveData<List<User>>()
//    val firebaseUserList : LiveData<List<User>> = _firebaseUserList
//
//    fun login(email: String, password: String){
//        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
//            if (it.isSuccessful){
//                _currentUser.value = it.result.user
//                getUserListWithOutCurrentUser(it.result.user)
//            }else{
//                //TODO Pop-Up Message das Login fehlgeschlagen
//            }
//        }
//    }
//
//    fun logout(){
//        firebaseAuth.signOut()
//        _currentUser.value = null
//    }
//
//
//    fun getUserListWithOutCurrentUser(currentUser: FirebaseUser?){
//        val userList = mutableListOf<User>()
//        if (currentUser != null){
//            firedatabase.collection("User").whereNotEqualTo("id", currentUser.uid)
//                .get()
//                .addOnSuccessListener {
//                    Log.d("TAG", it.toString())
//                    for (user in it){
//                        val cloudUser = user.toObject(User::class.java)
//                        userList.add(cloudUser)
//                    }
//                }
//                .addOnFailureListener {
//                    Log.d("TAG", "Loading UserList failed")
//                }
//
//            _firebaseUserList.postValue(userList)
//        }
//    }
//
//
//    fun getUserListFromFirebase(){
//        viewModelScope.launch {
//
//            try {
//                val userList = repository.getFirebaseDataUser()
//                _firebaseUserList.postValue(userList) // Post the value to ensure it's set on the main thread
//            } catch (e: Exception) {
//                _firebaseUserList.postValue(emptyList()) // Leere Liste setzen, falls ein Fehler auftritt
//            }
//        //_firebaseUserList.postValue(repository.getFirebaseDataUser())
//
//        }
//    }
//
//
//    fun createChatroom(user:User){
//
//        //TODO: Abfrage ob dieser Chatroom bereits existiert
//
//        val chatRoomId = UUID.randomUUID().toString()
//        val chatRoomName :String = loggedInUser.value?.firstName!! + user.firstName
//        val lastMessage: String = ""
//        var lastActivityTimestamp: Long = 0L
//        var chatParticipants: MutableList<String> = mutableListOf(loggedInUser.value?.userId!!, user.userId)
//
//        val chatroom: ChatRoom = ChatRoom(
//            chatRoomId = chatRoomId,
//            chatRoomName = chatRoomName,
//            lastMessage = lastMessage,
//            lastActivityTimestamp = lastActivityTimestamp,
//            chatParticipants = chatParticipants
//        )
//
//        firedatabase.collection("ChatRooms").document(chatRoomId).set(chatroom)
//    }










    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------FIREBASE-STORAGE---------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    val storage: FirebaseStorage = Firebase.storage

    // Create a storage reference from our app
    var storageRef = storage.reference

    val fileName : String = currentUser.value?.userId + "_profilePicture.jpg"
    var imagesRef: StorageReference? = storageRef.child("profilePictures/$fileName")
    var spaceRef = storageRef.child(fileName)



    fun uploadImage(uri: Uri){
        val fileName : String = profileRef.id + "_profilePicture.jpg"
        val imageRef = firebaseStorage.reference.child("images/${currentUser.value?.userId}/$fileName")
        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnCompleteListener{
            imageRef.downloadUrl.addOnCompleteListener {
                if(it.isSuccessful){
                    setImage(it.result)
                }
            }
        }
    }

    lateinit var profileRef: DocumentReference

//    init {
//        if(firebaseAuth.currentUser != null){
//            setUpUserEnv()
//        }
//    }

//    private fun setUpUserEnv(){
//        _currentUser.value = firebaseAuth.currentUser
//        profileRef = firedatabase.collection("User").document(firebaseAuth.currentUser?.uid!!)
//        profileRef.addSnapshotListener { snapShot, error ->
//            if(error == null && snapShot != null){
//                loggedInUser.value = snapShot.toObject(User::class.java)
//            }
//        }
//    }

    private fun setImage(uri: Uri){
        profileRef.update("image",uri.toString())
    }








    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------NEW APP--------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------














//    private val firebaseAuth = FirebaseAuth.getInstance()
//    private val firebaseStorage = FirebaseStorage.getInstance()
//    private val firedatabase = FirebaseFirestore.getInstance()
//
//    val firebaseUserList: LiveData<List<User>> = repository.firebaseUserList
//    val currentFirebaseUser: LiveData<FirebaseUser?> = repository.currentFirebaseUser
//    val currentUser = repository.currentUser
//    val firebaseChatRoomList = repository.firebaseChatRoomList
//    val currentChatRoom = repository.currentChatRoom
//    var currentChatPartner = User()


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

//    fun getFirebaseDataUser() {
//        if (currentFirebaseUser != null) {
//            viewModelScope.launch {
//                repository.getFirebaseDataUser()
//            }
//        }
//    }
//
//    fun setCurrentUser() {
//        if (currentFirebaseUser != null) {
//            viewModelScope.launch {
//                repository.setCurrentUser()
//            }
//        }
//    }
//
//    fun getFirebaseDataChatRooms() {
//        if (currentFirebaseUser != null) {
//            viewModelScope.launch {
//                repository.getFirebaseDataChatRooms()
//            }
//        }
//    }

    fun createChatroom(user:User){

        //TODO: Abfrage ob dieser Chatroom bereits existiert

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

    fun clearMessagelist(){
        repository.clearMessagelist()
    }

    fun loadData() {
        viewModelScope.launch {
            repository.getFirebaseDataUser()
            repository.setCurrentUser()
            repository.getChatRoomList()
            repository.getMessageList()
        }
    }

}
