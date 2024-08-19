package com.incirkus.connect

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.incirkus.connect.DATA.Model.APIResponse
import com.incirkus.connect.DATA.Model.Day
import com.incirkus.connect.DATA.Model.Holiday
import com.incirkus.connect.DATA.Model.Message
import com.incirkus.connect.DATA.Model.Month
import com.incirkus.connect.DATA.Model.User
import com.incirkus.connect.DATA.Repository
import com.incirkus.connect.DATA.local.getDataBase

import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.IsoFields
import java.util.Locale

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDataBase(application)
    val repository = Repository(database,this)

    val currentUserOld = repository.currentUser
    val userList = repository.userList
    val currentChatRoom = repository.currentChatRoom
    val currentChatParticipants = repository.currentChatParticipants
    val usersChatRoomIdList = repository.usersChatRoomIdList
    val usersChatRoomList = repository.usersChatRoomList


    fun preloadItems(){
        viewModelScope.launch {
            repository.preload()
            repository.loadCurrentUserFromUserList()
            repository.loadCurrentUser()
            //repository.loadUserList()
        }
    }

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

    fun createNewChatroom(currentUser: User, selectedUser: User){
        viewModelScope.launch {
            repository.createNewChatroom(currentUser,selectedUser)
        }
    }

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

    fun getChatRoom(chatRoomId:Long){
        viewModelScope.launch {
            repository.getChatRoom(chatRoomId)
        }
    }

    fun getChatParticipants(chatParticipantsId:Long){
        viewModelScope.launch {
            repository.getChatParticipants(chatParticipantsId)
        }
    }

    fun sendMessage(message: Message){
        viewModelScope.launch {
            try {

                repository.sendMessage(message)
            }catch (e:Exception){
                Log.e("ConnectTag", "ViewModel.sendMessage: Error sending message: ${e.message}")
            }
        }
    }

    fun loadUsersChatLists(){
        viewModelScope.launch {
            repository.loadUsersChatParticipantsLists()
        }
    }

    fun getOneUserById(userID: Long): LiveData<User>{
        lateinit var user: LiveData<User>
        viewModelScope.launch {
            user = repository.getOneUserById(userID)
        }
        return user
    }

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
    private val firebaseStore = FirebaseStorage.getInstance()

    private var _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    fun login(email: String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful){
                _currentUser.value = it.result.user
            }else{
                //TODO Pop-Up Message das Login fehlgeschlagen
            }
        }
    }

    fun logout(){
        firebaseAuth.signOut()
        _currentUser.value = null
    }
}
