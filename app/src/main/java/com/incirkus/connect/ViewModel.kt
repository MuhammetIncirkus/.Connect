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
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.incirkus.connect.DATA.Model.Attachment
import com.incirkus.connect.DATA.Model.ChatRoom
import com.incirkus.connect.DATA.Model.LeaveRequest

import kotlinx.coroutines.launch
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

    val repository = Repository()

    //Create Firebase Instances
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()
    private val firedatabase = FirebaseFirestore.getInstance()


    private val _isUploading = MutableLiveData<Boolean>()
    val isUploading: LiveData<Boolean> = _isUploading
    private val _isUploadingApi = MutableLiveData<Boolean>()
    val isUploadingApi: LiveData<Boolean> = _isUploadingApi

    private val _isWebViewVisible = MutableLiveData<Boolean>()
    val isWebViewVisible: LiveData<Boolean> = _isWebViewVisible

    private val _attachmentPath = MutableLiveData<String>()
    val attachmentPath: LiveData<String> = _attachmentPath

    val monthList = createMonthList()
    val actualMonth = getactualMonth()

    //Data from the repository

    //For Calendar
    val holidayList = repository.holidayList
    val firebaseLeaveRequestList = repository.firebaseLeaveRequestList

    //For Chat, Messages, Info, Attachments

    val firebaseUserList = repository.firebaseUserList
    var userSearchList: LiveData<List<User>> = repository.firebaseUserList
    private val _filteredUserList = MutableLiveData<List<User>>()
    val filteredUserList: LiveData<List<User>> = _filteredUserList
    val currentFirebaseUser = repository.currentFirebaseUser
    val currentUser = repository.currentUser
    val firebaseChatRoomList = repository.firebaseChatRoomList
    val currentChatRoom = repository.currentChatRoom
    var currentChatPartner = User()
    var _currentChatPartner = MutableLiveData<User>()
    val currentChatPartner2: LiveData<User> = _currentChatPartner
    val firebaseMessageList = repository.firebaseMessageList
    val firebaseAttachmentList = repository.firebaseAttachmentList

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------Calendar Items-------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Generates a list of `Month` objects, each representing a month within a range of time (from 12 months before the current date to 24 months after).
     * Each `Month` object contains information about the year, month number, month name, the length of the month, the weekday of the first day of the month,
     * and a list of `Day` objects representing the individual days of that month.
     *
     * ---
     *
     * Erzeugt eine Liste von `Month`-Objekten, die jeweils einen Monat innerhalb eines Zeitraums (von 12 Monaten vor dem aktuellen Datum bis 24 Monate danach) repräsentieren.
     * Jedes `Month`-Objekt enthält Informationen über das Jahr, die Monatsnummer, den Monatsnamen, die Länge des Monats, den Wochentag des ersten Tages des Monats
     * und eine Liste von `Day`-Objekten, die die einzelnen Tage dieses Monats darstellen.
     *
     * @return List<Month>: A list of `Month` objects, representing each month in the range of time.
     *                      Eine Liste von `Month`-Objekten, die jeden Monat im angegebenen Zeitraum repräsentieren.
     */
    private fun createMonthList(): List<Month> {

        val monthList: MutableList<Month> = mutableListOf()

        val currentDate = LocalDate.now()

        val startDate = currentDate.minusMonths(12)
        val endDate = currentDate.plusMonths(24)

        var yearMonth = YearMonth.from(startDate)

        while (yearMonth <= YearMonth.from(endDate)) {

            val year = yearMonth.year
            val monthNumber = yearMonth.month.value
            val monthString = yearMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
            val monthLength = yearMonth.lengthOfMonth()
            val firstDayOfMonth = LocalDate.of(year, monthNumber, 1)
            val firstDayOfMontAsWeekdayString =
                firstDayOfMonth.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
            val firstDayOfMontAsWeekdayInt = firstDayOfMonth.dayOfWeek.value

            val daylist: List<Day> = createDayList(year, monthNumber, monthLength)

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

    /**
     * Creates a list of `Day` objects representing each day of a specific month and year.
     * The list will also include placeholder days at the beginning, if the first day of the month does not start on a Monday.
     *
     * ---
     *
     * Erzeugt eine Liste von `Day`-Objekten, die jeden Tag eines bestimmten Monats und Jahres darstellen.
     * Die Liste enthält außerdem Platzhalter-Tage am Anfang, wenn der erste Tag des Monats nicht auf einen Montag fällt.
     *
     * @param year Int: The year of the month to create the days for. // Das Jahr des Monats, für das die Tage erstellt werden.
     * @param monthNumber Int: The month number (1-12) for which the days are created. // Die Monatsnummer (1-12), für die die Tage erstellt werden.
     * @param monthLength Int: The total number of days in the month. // Die Gesamtzahl der Tage im Monat.
     * @return List<Day>: A list of `Day` objects representing each day in the month, including placeholders for empty slots at the beginning of the list.
     *                    Eine Liste von `Day`-Objekten, die jeden Tag des Monats darstellen, einschließlich Platzhalter für leere Felder zu Beginn der Liste.
     */
    fun createDayList(year: Int, monthNumber: Int, monthLength: Int): List<Day> {

        val dayList: MutableList<Day> = mutableListOf()

        when (LocalDate.of(year, monthNumber, 1).dayOfWeek.value) {
            1 -> {
                fillDayList(year, monthNumber, monthLength, dayList)
            }

            2 -> {
                addPlaceholderDaysToDayList(dayList)
                fillDayList(year, monthNumber, monthLength, dayList)
            }

            3 -> {
                repeat(2) {
                    addPlaceholderDaysToDayList(dayList)
                }
                fillDayList(year, monthNumber, monthLength, dayList)
            }

            4 -> {
                repeat(3) {
                    addPlaceholderDaysToDayList(dayList)
                }
                fillDayList(year, monthNumber, monthLength, dayList)
            }

            5 -> {
                repeat(4) {
                    addPlaceholderDaysToDayList(dayList)
                }
                fillDayList(year, monthNumber, monthLength, dayList)
            }

            6 -> {
                repeat(5) {
                    addPlaceholderDaysToDayList(dayList)
                }
                fillDayList(year, monthNumber, monthLength, dayList)
            }

            7 -> {
                repeat(6) {
                    addPlaceholderDaysToDayList(dayList)
                }
                fillDayList(year, monthNumber, monthLength, dayList)
            }
        }

        return dayList
    }

    /**
     * Populates a list with `Day` objects representing each day of a given month and year.
     * Each `Day` object contains details such as the day of the month, the day of the week (both as a string and an integer),
     * and the ISO-based calendar week of the day.
     *
     * ---
     *
     * Füllt eine Liste mit `Day`-Objekten, die jeden Tag eines bestimmten Monats und Jahres darstellen.
     * Jedes `Day`-Objekt enthält Informationen wie den Tag des Monats, den Wochentag (als String und Zahl)
     * und die kalenderbasierte Woche nach dem ISO-Standard.
     *
     * @param year Int: The year of the month being processed. // Das Jahr des Monats, das verarbeitet wird.
     * @param monthNumber Int: The month number (1-12) being processed. // Die Monatsnummer (1-12), die verarbeitet wird.
     * @param monthLength Int: The total number of days in the month. // Die Gesamtzahl der Tage im Monat.
     * @param dayList MutableList<Day>: A mutable list that will be filled with `Day` objects for each day in the month.
     *                                    Eine veränderbare Liste, die mit `Day`-Objekten für jeden Tag des Monats gefüllt wird.
     */
    private fun fillDayList(year: Int, monthNumber: Int, monthLength: Int, dayList: MutableList<Day>) {

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

    /**
     * Adds a placeholder `Day` object to the given list.
     * The placeholder is used to fill the start of a month when the first day does not start on a Monday.
     * This ensures that the calendar grid remains aligned properly.
     *
     * ---
     *
     * Fügt der übergebenen Liste ein Platzhalter-`Day`-Objekt hinzu.
     * Der Platzhalter wird verwendet, um den Anfang eines Monats zu füllen, wenn der erste Tag nicht auf einen Montag fällt.
     * Dies stellt sicher, dass das Kalender-Raster korrekt ausgerichtet bleibt.
     *
     * @param dayList MutableList<Day>: A mutable list to which the placeholder `Day` object will be added.
     *                                    Eine veränderbare Liste, der das Platzhalter-`Day`-Objekt hinzugefügt wird.
     */
    private fun addPlaceholderDaysToDayList(dayList: MutableList<Day>) {

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

    /**
     * Retrieves the current month as a `Month` object, based on the current system date.
     * It gathers information about the month, including the year, month number, month name,
     * length of the month, and the first day's weekday (both as a string and integer).
     * The function also generates a list of `Day` objects for each day in the current month.
     *
     * ---
     *
     * Ruft den aktuellen Monat als `Month`-Objekt ab, basierend auf dem aktuellen Systemdatum.
     * Es werden Informationen über den Monat gesammelt, einschließlich Jahr, Monatsnummer, Monatsname,
     * Länge des Monats und der Wochentag des ersten Tages (sowohl als String als auch als Integer).
     * Die Funktion erstellt auch eine Liste von `Day`-Objekten für jeden Tag des aktuellen Monats.
     *
     * @return Month: The current month represented as a `Month` object with a full list of days.
     *                Der aktuelle Monat, dargestellt als `Month`-Objekt mit einer vollständigen Liste der Tage.
     */
    fun getactualMonth(): Month {

        val currentDate = LocalDate.now()

        var yearMonth = YearMonth.from(currentDate)

        val year = yearMonth.year
        val monthNumber = yearMonth.month.value
        val monthString = yearMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
        val monthLength = yearMonth.lengthOfMonth()
        val firstDayOfMonth = LocalDate.of(year, monthNumber, 1)
        val firstDayOfMontAsWeekdayString =
            firstDayOfMonth.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        val firstDayOfMontAsWeekdayInt = firstDayOfMonth.dayOfWeek.value

        val daylist: List<Day> = createDayList(year, monthNumber, monthLength)

        val month = Month(
            year = year,
            monthNumber = monthNumber,
            monthString = monthString,
            monthLength = monthLength,
            firstDayOfMontAsWeekdayString = firstDayOfMontAsWeekdayString,
            firstDayOfMontAsWeekdayInt = firstDayOfMontAsWeekdayInt,
            daylist = daylist
        )

        return month

    }


//-----------------------------------------------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------API----------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Retrieves a list of holidays asynchronously and updates the `holidayList` if it is currently empty.
     * If `holidayList` is empty, the function launches a coroutine within the `viewModelScope` to fetch
     * the holiday data from the repository. It then converts the received response into a list of holidays.
     *
     * ---
     *
     * Ruft asynchron eine Liste von Feiertagen ab und aktualisiert `holidayList`, falls diese derzeit leer ist.
     * Falls `holidayList` leer ist, startet die Funktion eine Coroutine innerhalb des `viewModelScope`, um
     * die Feiertagsdaten vom Repository abzurufen. Anschließend wird die empfangene Antwort in eine Liste von
     * Feiertagen umgewandelt.
     */
    fun getListForHolidays() {
        if (holidayList.isEmpty()) {
            viewModelScope.launch {
                var responseList = repository.getHolidayList()
                convertResponseToHolidayList(responseList)
            }
        }
    }

    /**
     * Initiates a manual update for the list of holidays by first setting the `_isUploadingApi` flag to `true`.
     * It then launches a coroutine within the `viewModelScope` to fetch the holiday data from the repository
     * and subsequently deletes the current holiday list based on the retrieved response.
     *
     * ---
     *
     * Beginnt eine manuelle Aktualisierung der Liste von Feiertagen, indem zuerst das Flag `_isUploadingApi` auf `true`
     * gesetzt wird. Anschließend wird eine Coroutine innerhalb des `viewModelScope` gestartet, um die Feiertagsdaten
     * vom Repository abzurufen und anschließend die aktuelle Feiertagsliste basierend auf der abgerufenen Antwort zu löschen.
     */
    fun getListForHolidaysManual() {
        _isUploadingApi.value = true
        viewModelScope.launch {
            var responseList = repository.getHolidayList()
            deleteHolidayList(responseList)
        }
    }

    /**
     * Retrieves a list of holidays for specified states from a remote API and updates the local holiday list.
     *
     * This function sets the `_isUploadingApi` flag to `true` to indicate that an API request is in progress.
     * It then launches a coroutine within the `viewModelScope` to asynchronously fetch holiday data for the
     * given states from the repository. After retrieving the data, it deletes the existing holiday list based on
     * the retrieved response.
     *
     * ---
     *
     * Ruft eine Liste von Feiertagen für die angegebenen Bundesländer von einer externen API ab und aktualisiert die
     * lokale Feiertagsliste.
     *
     * Diese Funktion setzt das Flag `_isUploadingApi` auf `true`, um anzuzeigen, dass eine API-Anfrage läuft. Anschließend
     * wird eine Coroutine innerhalb des `viewModelScope` gestartet, um die Feiertagsdaten für die angegebenen Bundesländer
     * asynchron vom Repository abzurufen. Nach dem Abrufen der Daten wird die bestehende Feiertagsliste basierend auf
     * der abgerufenen Antwort gelöscht.
     *
     * @param states String: Eine durch Kommas getrennte Liste von Bundesländern, für die die Feiertage abgerufen werden sollen.
     *               Eine durch Kommas getrennte Liste von Bundesländern, für die die Feiertage abgerufen werden sollen.
     */
    fun getHolidayListForSomeStates(states: String) {
        _isUploadingApi.value = true
        viewModelScope.launch {
            var responseList = repository.getHolidayListForSomeStates(states)
            deleteHolidayList(responseList)
        }
    }

    /**
     * Deletes all documents in the "HolidayList" subcollection of the current user and updates the holiday list
     * based on the provided API response.
     *
     * This function retrieves all documents in the "HolidayList" subcollection for the current user and initiates
     * deletion of each document. After the deletion operations are completed, it updates the local holiday list
     * by converting the provided API response.
     *
     * ---
     *
     * Löscht alle Dokumente in der "HolidayList"-Unterkollektion des aktuellen Benutzers und aktualisiert die
     * Feiertagsliste basierend auf der bereitgestellten API-Antwort.
     *
     * Diese Funktion ruft alle Dokumente in der "HolidayList"-Unterkollektion für den aktuellen Benutzer ab und
     * initiiert das Löschen jedes Dokuments. Nachdem die Löschvorgänge abgeschlossen sind, wird die lokale
     * Feiertagsliste aktualisiert, indem die bereitgestellte API-Antwort konvertiert wird.
     *
     * @param responseList APIResponse: Die Antwort der API, die die Feiertagsdaten enthält, mit denen die lokale
     *                      Feiertagsliste aktualisiert werden soll.
     * @see [convertResponseToHolidayList]
     */
    fun deleteHolidayList(responseList: APIResponse) {
        val holidayListRef = firedatabase.collection("User").document(currentUser.value!!.userId)
            .collection("HolidayList")

        // Abrufen aller Dokumente in der HolidayList-Unterkollektion
        holidayListRef.get().addOnSuccessListener { querySnapshot ->
            val deleteTasks = mutableListOf<Task<Void>>()
            // Für jedes Dokument in der HolidayList-Unterkollektion
            if (!querySnapshot.isEmpty) {
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

    /**
     * Converts the provided API response into a list of `Holiday` objects and stores them in the Firestore database.
     *
     * This function processes the `APIResponse` object to extract holiday data and convert it into `Holiday`
     * objects. It then saves these objects in the "HolidayList" subcollection of the current user in the Firestore
     * database. The function also updates the local `holidayList` with the newly created `Holiday` objects.
     *
     * ---
     *
     * Konvertiert die bereitgestellte API-Antwort in eine Liste von `Holiday`-Objekten und speichert diese in der
     * Firestore-Datenbank.
     *
     * Diese Funktion verarbeitet das `APIResponse`-Objekt, um Feiertagsdaten zu extrahieren und in `Holiday`-Objekte
     * umzuwandeln. Diese Objekte werden dann in der "HolidayList"-Unterkollektion des aktuellen Benutzers in der
     * Firestore-Datenbank gespeichert. Die Funktion aktualisiert auch die lokale `holidayList` mit den neu erstellten
     * `Holiday`-Objekten.
     *
     * @param responseList APIResponse: Die Antwort der API, die Feiertagsdaten enthält, die in `Holiday`-Objekte
     *                      umgewandelt und in der Firestore-Datenbank gespeichert werden sollen.
     *
     */
    fun convertResponseToHolidayList(responseList: APIResponse) {

        if (responseList != null) {

            for (holiday in responseList.feiertage) {
                val name: String = holiday.fname
                val date: String = holiday.date

                var year: Int = date.split("-")[0].toInt()
                var month: Int = date.split("-")[1].toInt()
                var day: Int = date.split("-")[2].toInt()

                var region = ""
                if (holiday.all_states == "1") {
                    region = "DE"
                } else {
                    if (holiday.bw == "1") {
                        region = region + "bw,"
                    }
                    if (holiday.by == "1") {
                        region = region + "by,"
                    }
                    if (holiday.be == "1") {
                        region = region + "be,"
                    }
                    if (holiday.bb == "1") {
                        region = region + "bb,"
                    }
                    if (holiday.hb == "1") {
                        region = region + "hb,"
                    }
                    if (holiday.hh == "1") {
                        region = region + "hh,"
                    }
                    if (holiday.he == "1") {
                        region = region + "he,"
                    }
                    if (holiday.mv == "1") {
                        region = region + "mv,"
                    }
                    if (holiday.ni == "1") {
                        region = region + "ni,"
                    }
                    if (holiday.nw == "1") {
                        region = region + "nw,"
                    }
                    if (holiday.rp == "1") {
                        region = region + "rp,"
                    }
                    if (holiday.sl == "1") {
                        region = region + "sl,"
                    }
                    if (holiday.sn == "1") {
                        region = region + "sn,"
                    }
                    if (holiday.st == "1") {
                        region = region + "st,"
                    }
                    if (holiday.sh == "1") {
                        region = region + "sh,"
                    }
                    if (holiday.th == "1") {
                        region = region + "th,"
                    }

                }
                val comment: String = holiday.comment
                var augsburg: Boolean = false
                var katholisch: Boolean = false

                if (holiday.augsburg != null) {
                    augsburg = true
                }

                if (holiday.katholisch != null) {
                    katholisch = true
                }

                val holiday: Holiday = Holiday(

                    holidayId = UUID.randomUUID().toString(),
                    holidayName = name,
                    holidayRegion = region,
                    holidayDay = day,
                    holidayMonth = month,
                    holidayYear = year,
                    comment = comment,
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
        _isUploadingApi.value = false
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------FIREBASE-STORAGE---------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Uploads an image to Firebase Storage and updates the user's profile with the image URL.
     *
     * This function uploads an image file from the given URI to Firebase Storage under a path that includes
     * the user's ID. Once the upload is complete, it retrieves the download URL of the uploaded image and
     * updates the current user's profile with this URL. The function also updates the `_isUploading` LiveData
     * to reflect the upload status.
     *
     * ---
     *
     * Lädt ein Bild in Firebase Storage hoch und aktualisiert das Profil des Benutzers mit der Bild-URL.
     *
     * Diese Funktion lädt eine Bilddatei von der angegebenen URI in Firebase Storage hoch, wobei der Pfad die
     * Benutzer-ID enthält. Sobald der Upload abgeschlossen ist, wird die Download-URL des hochgeladenen Bildes
     * abgerufen und das Profil des aktuellen Benutzers mit dieser URL aktualisiert. Die Funktion aktualisiert
     * auch das `_isUploading` LiveData, um den Upload-Status widerzuspiegeln.
     *
     * @param uri Uri: Die URI des Bildes, das hochgeladen werden soll.
     *
     * @see [updateCurrentUser]
     */
    fun uploadImage(uri: Uri) {
        _isUploading.value = true
        val fileName: String = currentUser.value?.userId + "_profilePicture.jpg"
        val imageRef =
            firebaseStorage.reference.child("images/${currentUser.value?.userId}/$fileName")
        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnCompleteListener {
            imageRef.downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {
                    currentUser.value?.image = it.result.toString()
                    updateCurrentUser()
                }
            }
        }
    }

    /**
     * Updates the current user's profile in Firestore with the new profile image URL.
     *
     * This function updates the `image` field of the current user's document in the Firestore database
     * with the URL of the newly uploaded profile image. It logs the success or failure of the update operation.
     * The function also sets the `_isUploading` LiveData to `false` to indicate that the upload process is complete.
     *
     * ---
     *
     * Aktualisiert das Profil des aktuellen Benutzers in Firestore mit der neuen Profilbild-URL.
     *
     * Diese Funktion aktualisiert das Feld `image` des Dokuments des aktuellen Benutzers in der Firestore-Datenbank
     * mit der URL des neu hochgeladenen Profilbildes. Es protokolliert den Erfolg oder das Scheitern der
     * Aktualisierungsoperation. Die Funktion setzt auch das `_isUploading` LiveData auf `false`, um anzuzeigen,
     * dass der Upload-Prozess abgeschlossen ist.
     *
     *
     * @return Unit
     */
    private fun updateCurrentUser() {
        val currentUserRef = currentUser.value?.let {
            firedatabase.collection("User").document(
                it.userId
            )
        }
        if (currentUserRef != null) {
            currentUserRef
                .update("image", currentUser.value?.image)
                .addOnSuccessListener {
                    Log.i(
                        "Firebase",
                        "ViewModel: updateCurrentUser: ${currentUser.value?.image}"
                    )
                }
                .addOnFailureListener { e ->
                    Log.i(
                        "Firebase",
                        "ViewModel: updateCurrentUser: ${e.toString()}"
                    )
                }
        }
        _isUploading.value = false
    }

    /**
     * Uploads an attachment to Firebase Storage and updates the attachment's path with the download URL.
     *
     * This function uploads the given attachment file to Firebase Storage under a path that includes the chat room ID
     * and the attachment's name. Once the upload is complete, it retrieves the download URL and updates the attachment's
     * path. It then calls `sendAttachment` to process the updated attachment.
     *
     * ---
     *
     * Lädt eine Datei als Anhang in Firebase Storage hoch und aktualisiert den Pfad des Anhangs mit der Download-URL.
     *
     * Diese Funktion lädt die gegebene Anhangsdatei in Firebase Storage unter einem Pfad hoch, der die Chatraum-ID
     * und den Namen des Anhangs enthält. Sobald der Upload abgeschlossen ist, wird die Download-URL abgerufen und der
     * Pfad des Anhangs aktualisiert. Anschließend wird `sendAttachment` aufgerufen, um den aktualisierten Anhang zu verarbeiten.
     *
     * @param uri The URI of the file to upload.
     * @param attachment The attachment object that contains metadata about the file.
     *
     * @see [sendAttachment]
     */
    fun uploadAttachment(uri: Uri, attachment: Attachment) {
        _isUploading.value = true
        val chatRoomId = attachment.chatRoomId
        val filename = attachment.attachmentName
        val storageRef = firebaseStorage.reference.child("Attachment/$chatRoomId/$filename")
        val uploadTask = storageRef.putFile(uri)

        uploadTask.addOnCompleteListener {
            storageRef.downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {
                    attachment.path = it.result.toString()
                    sendAttachment(attachment)
                }
            }
        }
    }

    /**
     * Sends an attachment and updates the chat room with the latest message details.
     *
     * This function uploads the attachment to Firebase Firestore and creates a message object
     * representing the attachment. It then sends the message using the `sendMessage` function.
     * The chat room's last message details are updated with the attachment's information,
     * and the chat room is updated using the `updateChatRoom` function.
     *
     * ---
     *
     * Sendet einen Anhang und aktualisiert den Chatraum mit den neuesten Nachrichteninformationen.
     *
     * Diese Funktion lädt den Anhang in Firebase Firestore hoch und erstellt ein Nachrichtenobjekt,
     * das den Anhang darstellt. Anschließend wird die Nachricht mit der Funktion `sendMessage` gesendet.
     * Die letzten Nachrichteninformationen des Chatraums werden mit den Informationen des Anhangs aktualisiert,
     * und der Chatraum wird mit der Funktion `updateChatRoom` aktualisiert.
     *
     * @param attachment The attachment object containing metadata about the file.
     *
     * @see [sendMessage]
     * @see [updateChatRoom]
     */
    private fun sendAttachment(attachment: Attachment) {

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
        _isUploading.value = false
    }


    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------Firebase FireStore--------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Authenticates a user with email and password and logs them in.
     *
     * This function uses Firebase Authentication to sign in a user with the provided email
     * and password. Upon successful login, it sets the current Firebase user in the repository
     * and initiates loading of user-specific data.
     *
     * ---
     *
     * Authentifiziert einen Benutzer mit E-Mail und Passwort und meldet ihn an.
     *
     * Diese Funktion verwendet die Firebase-Authentifizierung, um einen Benutzer mit der angegebenen
     * E-Mail und dem Passwort anzumelden. Bei erfolgreicher Anmeldung wird der aktuelle Firebase-Benutzer
     * im Repository gesetzt und das Laden benutzerspezifischer Daten gestartet.
     *
     * @param email The email address of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     *
     * @see [loadData]
     */
    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                viewModelScope.launch {
                    repository.setCurrentFirebaseUser(it.result.user)
                    loadData()
                }
            }
        }
    }

    /**
     * Signs out the current user from Firebase Authentication and performs additional logout operations.
     *
     * This function uses Firebase Authentication to sign out the currently authenticated user.
     * After signing out, it also calls the `logout` method in the repository to perform any
     * additional cleanup or state updates necessary for the logout process.
     *
     * ---
     *
     * Meldet den aktuellen Benutzer bei Firebase Authentication ab und führt zusätzliche Abmeldevorgänge durch.
     *
     * Diese Funktion verwendet die Firebase-Authentifizierung, um den aktuell authentifizierten Benutzer abzumelden.
     * Nach der Abmeldung wird auch die Methode `logout` im Repository aufgerufen, um alle notwendigen
     * Bereinigungen oder Statusaktualisierungen für den Abmeldeprozess durchzuführen.
     *
     * @see [repository.logout]
     *
     */
    fun logout() {
        firebaseAuth.signOut()
        repository.logout()
    }

    /**
     * Creates a chat room between the current user and the specified user if it does not already exist.
     *
     * This function checks if a chat room already exists between the current user and the specified user.
     * If a chat room exists, it sets the current chat room and retrieves the chat partner.
     * If no chat room exists, it creates a new chat room, stores it in the database, sets the current chat room,
     * and retrieves the chat partner.
     *
     * ---
     *
     * Erstellt einen Chatraum zwischen dem aktuellen Benutzer und dem angegebenen Benutzer, wenn dieser noch nicht existiert.
     *
     * Diese Funktion überprüft, ob bereits ein Chatraum zwischen dem aktuellen Benutzer und dem angegebenen Benutzer existiert.
     * Wenn ein Chatraum existiert, wird der aktuelle Chatraum gesetzt und der Chat-Partner abgerufen.
     * Wenn kein Chatraum existiert, wird ein neuer Chatraum erstellt, in der Datenbank gespeichert, der aktuelle Chatraum gesetzt
     * und der Chat-Partner abgerufen.
     *
     * @param user Der Benutzer, mit dem der Chatraum erstellt werden soll.
     *
     * @return Boolean Gibt `true` zurück, wenn der Chatraum bereits existiert, andernfalls `false`.
     *
     * @see [getCurrentChatRoom]
     * @see [setCurrentChatroom]
     * @see [getChatPartner]
     */
    fun createChatroom(user: User): Boolean {

        var checkIfChatRoomExists: Boolean = false
        if (!firebaseChatRoomList.value.isNullOrEmpty()) {

            for (chatRoom in firebaseChatRoomList.value!!) {
                if (chatRoom.chatParticipants.containsAll(
                        listOf(
                            currentUser.value?.userId!!,
                            user.userId
                        )
                    )
                ) {
                    checkIfChatRoomExists = true
                    getCurrentChatRoom(chatRoom.chatRoomId)
                    setCurrentChatroom(chatRoom)
                    getChatPartner(user.userId)
                }
            }
        }

        if (!checkIfChatRoomExists) {

            val chatRoomId = UUID.randomUUID().toString()
            val chatRoomName: String = currentUser.value?.firstName!! + user.firstName
            val lastMessage: String = ""
            var lastActivityTimestamp: Long = 0L
            var chatParticipants: MutableList<String?> =
                mutableListOf(currentUser.value?.userId!!, user.userId)

            val chatroom: ChatRoom = ChatRoom(
                chatRoomId = chatRoomId,
                chatRoomName = chatRoomName,
                lastMessage = lastMessage,
                lastActivityTimestamp = lastActivityTimestamp,
                chatParticipants = chatParticipants
            )

            firedatabase.collection("ChatRooms").document(chatRoomId).set(chatroom)

            getCurrentChatRoom(chatRoomId)

            setCurrentChatroom(chatroom)
            getChatPartner(user.userId)
        }
        return checkIfChatRoomExists
    }

    /**
     * Retrieves the current chat room details based on the provided chat room ID.
     *
     * This function fetches details of a specific chat room from the repository using the provided `chatRoomId`.
     * It is called within a coroutine to ensure it operates asynchronously. This method is only executed if
     * `currentFirebaseUser` is not null.
     *
     * ---
     *
     * Ruft die Details des aktuellen Chatraums basierend auf der bereitgestellten Chatraum-ID ab.
     *
     * Diese Funktion ruft Details eines bestimmten Chatraums vom Repository unter Verwendung der bereitgestellten `chatRoomId` ab.
     * Sie wird innerhalb einer Coroutine aufgerufen, um sicherzustellen, dass sie asynchron ausgeführt wird. Diese Methode wird
     * nur ausgeführt, wenn `currentFirebaseUser` nicht null ist.
     *
     * @param chatRoomId Die ID des Chatraums, dessen Details abgerufen werden sollen.
     *
     * @see [repository.getCurrentChatRoom]
     */
    private fun getCurrentChatRoom(chatRoomId: String) {
        if (currentFirebaseUser != null) {
            viewModelScope.launch {
                repository.getCurrentChatRoom(chatRoomId)
            }
        }
    }

    /**
     * Sets the current chat room and updates the chat partner details.
     *
     * This function updates the current chat room in the repository with the provided `chatRoom` object.
     * It then retrieves the chat partner ID from the current chat room and, if the ID is not null,
     * fetches the details of the chat partner.
     *
     * ---
     *
     * Setzt den aktuellen Chatraum und aktualisiert die Details des Chatpartners.
     *
     * Diese Funktion aktualisiert den aktuellen Chatraum im Repository mit dem bereitgestellten `chatRoom`-Objekt.
     * Anschließend wird die Chatpartner-ID aus dem aktuellen Chatraum abgerufen und, falls die ID nicht null ist,
     * werden die Details des Chatpartners abgerufen.
     *
     * @param chatRoom Das `ChatRoom`-Objekt, das als aktueller Chatraum festgelegt werden soll.
     *
     * @see [repository.setCurrentChatroom]
     * @see [getChatPartnerID]
     * @see [getChatPartner]
     */
    fun setCurrentChatroom(chatRoom: ChatRoom) {
        repository.setCurrentChatroom(chatRoom)
        val chatPartnerID = getChatPartnerID(chatRoom)
        if (chatPartnerID != null) {
            getChatPartner(chatPartnerID)
        }
    }

    /**
     * Sends a message by storing it in the Firestore database.
     *
     * This function saves the provided `message` object to the "Messages" collection in Firestore.
     * The message is identified by its unique `messageId`, which is used as the document ID in the collection.
     *
     * ---
     *
     * Sendet eine Nachricht, indem sie in der Firestore-Datenbank gespeichert wird.
     *
     * Diese Funktion speichert das bereitgestellte `message`-Objekt in der Sammlung "Messages" in Firestore.
     * Die Nachricht wird durch ihre eindeutige `messageId` identifiziert, die als Dokument-ID in der Sammlung verwendet wird.
     *
     * @param message Das `Message`-Objekt, das gesendet und in der Datenbank gespeichert werden soll.
     */
    fun sendMessage(message: Message) {
        firedatabase.collection("Messages").document(message.messageId).set(message)
    }

    /**
     * Updates the current chat room in the Firestore database with the latest information.
     *
     * This function updates the "ChatRooms" collection in Firestore with the most recent values
     * for the following fields in the current chat room:
     * - `lastMessage`: The text of the last message sent in the chat room.
     * - `lastActivityTimestamp`: The timestamp of the last activity in the chat room.
     * - `lastMessageSenderId`: The ID of the user who sent the last message.
     *
     * The updates are performed individually for each field, and success or failure is logged.
     *
     * ---
     *
     * Aktualisiert den aktuellen Chatraum in der Firestore-Datenbank mit den neuesten Informationen.
     *
     * Diese Funktion aktualisiert die Sammlung "ChatRooms" in Firestore mit den aktuellsten Werten
     * für die folgenden Felder im aktuellen Chatraum:
     * - `lastMessage`: Der Text der zuletzt gesendeten Nachricht im Chatraum.
     * - `lastActivityTimestamp`: Der Zeitstempel der letzten Aktivität im Chatraum.
     * - `lastMessageSenderId`: Die ID des Benutzers, der die letzte Nachricht gesendet hat.
     *
     * Die Aktualisierungen werden einzeln für jedes Feld durchgeführt, und Erfolg oder Fehler werden protokolliert.
     */
    fun updateChatRoom() {
        val chatRoomRef = currentChatRoom.value?.let {
            firedatabase.collection("ChatRooms").document(
                it.chatRoomId
            )
        }
        if (chatRoomRef != null) {
            chatRoomRef
                .update("lastMessage", currentChatRoom.value?.lastMessage)
                .addOnSuccessListener {
                    Log.i(
                        "Firebase",
                        "ViewModel: updateChatRoom: ${currentChatRoom.value?.lastMessage}"
                    )
                }
                .addOnFailureListener { e ->
                    Log.i(
                        "Firebase",
                        "ViewModel: updateChatRoom: ${e.toString()}"
                    )
                }
            chatRoomRef
                .update("lastActivityTimestamp", currentChatRoom.value?.lastActivityTimestamp)
                .addOnSuccessListener {
                    Log.i(
                        "Firebase",
                        "ViewModel: updateChatRoom: ${currentChatRoom.value?.lastActivityTimestamp}"
                    )
                }
                .addOnFailureListener { e ->
                    Log.i(
                        "Firebase",
                        "ViewModel: updateChatRoom: ${e.toString()}"
                    )
                }
            chatRoomRef
                .update("lastMessageSenderId", currentChatRoom.value?.lastMessageSenderId)
                .addOnSuccessListener {
                    Log.i(
                        "Firebase",
                        "ViewModel: updateChatRoom: ${currentChatRoom.value?.lastMessageSenderId}"
                    )
                }
                .addOnFailureListener { e ->
                    Log.i(
                        "Firebase",
                        "ViewModel: updateChatRoom: ${e.toString()}"
                    )
                }
        }

    }

    /**
     * Retrieves the chat partner's user ID from the given chat room.
     *
     * This function iterates through the list of chat participants in the specified [ChatRoom] and
     * returns the ID of the participant who is not the current user. If there are multiple participants,
     * it will return the ID of the first one found. If the current user is the only participant, it returns null.
     *
     * ---
     *
     * Ruft die Benutzer-ID des Chatpartners aus dem angegebenen Chatraum ab.
     *
     * Diese Funktion durchläuft die Liste der Chatteilnehmer im angegebenen [ChatRoom] und gibt die ID des
     * Teilnehmers zurück, der nicht der aktuelle Benutzer ist. Wenn es mehrere Teilnehmer gibt, wird die ID des
     * zuerst gefundenen zurückgegeben. Wenn der aktuelle Benutzer der einzige Teilnehmer ist, wird null zurückgegeben.
     *
     * @param chatRoom The [ChatRoom] object containing the list of chat participants.
     * @return The user ID of the chat partner, or null if the current user is the only participant.
     */
    fun getChatPartnerID(chatRoom: ChatRoom): String? {
        var chatPartnerID: String? = ""

        for (id in chatRoom.chatParticipants) {
            if (id != currentUser.value?.userId) {
                chatPartnerID = id
            }
        }
        return chatPartnerID
    }

    /**
     * Retrieves the user object corresponding to the given user ID from the list of users.
     *
     * This function searches through the list of users and returns the [User] object with the matching user ID.
     * It also updates the current chat partner values.
     *
     * ---
     *
     * Ruft das Benutzerobjekt ab, das der angegebenen Benutzer-ID aus der Liste der Benutzer entspricht.
     *
     * Diese Funktion durchsucht die Liste der Benutzer und gibt das [User]-Objekt mit der übereinstimmenden Benutzer-ID zurück.
     * Es aktualisiert auch die aktuellen Chatpartner-Werte.
     *
     * @param id The user ID of the chat partner to retrieve.
     * @return The [User] object corresponding to the given user ID.
     */
    fun getChatPartner(id: String): User {

        var chatPartner: User = User()

        for (oneUser in firebaseUserList.value!!) {
            if (oneUser.userId == id)
                chatPartner = oneUser
        }
        _currentChatPartner.value = chatPartner
        currentChatPartner = chatPartner
        return chatPartner
    }

    /**
     * Converts a timestamp to a human-readable date and time string.
     *
     * This function takes a timestamp (in milliseconds) and converts it to a formatted date and time string.
     * If the date of the timestamp is today, it returns only the time in "HH:mm" format.
     * If the date is not today, it returns both the date and time in "dd.MM.yyyy HH:mm" format.
     *
     * ---
     *
     * Wandelt einen Zeitstempel (in Millisekunden) in einen lesbaren Datums- und Zeitstring um.
     * Wenn das Datum des Zeitstempels heute ist, wird nur die Zeit im Format "HH:mm" zurückgegeben.
     * Wenn das Datum nicht heute ist, werden sowohl Datum als auch Zeit im Format "dd.MM.yyyy HH:mm" zurückgegeben.
     *
     * @param timestamp The timestamp in milliseconds to be converted.
     * @return A formatted string representing the date and time corresponding to the provided timestamp.
     */
    fun convertTimestampToDate(timestamp: Long): String {
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
            val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            zonedDateTime.format(dateTimeFormatter)
        }
    }

    /**
     * Converts a timestamp to a formatted date string.
     *
     * This function takes a timestamp (in milliseconds) and converts it to a date string in "dd.MM.yyyy" format.
     *
     * ---
     *
     * Wandelt einen Zeitstempel (in Millisekunden) in einen Datumsstring im Format "dd.MM.yyyy" um.
     *
     * @param timestamp The timestamp in milliseconds to be converted.
     * @return A formatted string representing the date corresponding to the provided timestamp.
     */
    fun convertTimestampToDates(timestamp: Long): String {
        val instant = Instant.ofEpochMilli(timestamp)
        val zoneId = ZoneId.systemDefault()
        val zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId)

        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        return zonedDateTime.format(dateTimeFormatter)
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

    /**
     * Filters the list of users based on a search term.
     *
     * This function updates the `_filteredUserList` with users whose full name or department contains the search term (`filterText`).
     * If the search term is empty, the function resets the `_filteredUserList` to the original `userSearchList`.
     *
     * ---------
     *
     * Die Funktion aktualisiert die `_filteredUserList` mit Benutzern, deren Vollname oder Abteilung den Suchbegriff (`filterText`) enthält.
     * Wenn der Suchbegriff leer ist, wird die `_filteredUserList` auf die ursprüngliche `userSearchList` zurückgesetzt.
     *
     * @param filterText The text used to filter the list of users. If empty, the entire user list is shown.
     *                   Der Text, der verwendet wird, um die Liste der Benutzer zu filtern. Falls leer, wird die gesamte Benutzerliste angezeigt.
     */
    fun searchFilter(filterText: String) {
        if (!userSearchList.value.isNullOrEmpty()) {
            val currentList = userSearchList.value ?: return
            if (filterText.isEmpty()) {
                _filteredUserList.value = currentList
            } else {
                _filteredUserList.value = currentList.filter {
                    it.fullName!!.contains(filterText, true) || it.department!!.contains(filterText,true)
                }
            }
        }
    }

    /**
     * Submits a leave request to the Firestore database.
     *
     * This function stores a `LeaveRequest` document in the `LeaveRequest` collection within Firestore.
     * The document is identified by its unique `requestId`, which is obtained from the `LeaveRequest` object.
     * The leave request details are saved as provided in the `LeaveRequest` parameter.
     *
     * ---
     *
     * Reicht einen Urlaubsantrag in der Firestore-Datenbank ein.
     *
     * Diese Funktion speichert ein `LeaveRequest`-Dokument in der Sammlung `LeaveRequest` innerhalb von Firestore.
     * Das Dokument wird durch seine eindeutige `requestId` identifiziert, die vom `LeaveRequest`-Objekt stammt.
     * Die Einzelheiten des Urlaubsantrags werden entsprechend dem `LeaveRequest`-Parameter gespeichert.
     *
     * @param leaveRequest LeaveRequest: Das Urlaubsantragsobjekt, das die Einzelheiten des einzureichenden Urlaubsantrags enthält.
     */
    fun submitLeaveRequest(leaveRequest: LeaveRequest) {
        firedatabase.collection("LeaveRequest").document(leaveRequest.requestId).set(leaveRequest)
    }

    /**
     * Sends a password reset email to the specified email address.
     *
     * This function uses Firebase Authentication to send a password reset email to the user associated with the provided
     * email address. It takes two callback functions: `onSuccess` which is invoked if the password reset email is sent
     * successfully, and `onFailure` which is invoked if there is an error sending the email.
     *
     * ---
     *
     * Sendet eine E-Mail zum Zurücksetzen des Passworts an die angegebene E-Mail-Adresse.
     *
     * Diese Funktion verwendet Firebase Authentication, um eine E-Mail zum Zurücksetzen des Passworts an den Benutzer
     * zu senden, der mit der angegebenen E-Mail-Adresse verknüpft ist. Es werden zwei Callback-Funktionen übergeben: `onSuccess`,
     * die aufgerufen wird, wenn die E-Mail zum Zurücksetzen des Passworts erfolgreich gesendet wurde, und `onFailure`,
     * die aufgerufen wird, wenn ein Fehler beim Senden der E-Mail auftritt.
     *
     * @param email String: Die E-Mail-Adresse des Benutzers, an den die Passwort-Zurücksetzungs-E-Mail gesendet werden soll.
     * @param onSuccess () -> Unit: Die Callback-Funktion, die aufgerufen wird, wenn die E-Mail erfolgreich gesendet wurde.
     * @param onFailure () -> Unit: Die Callback-Funktion, die aufgerufen wird, wenn beim Senden der E-Mail ein Fehler auftritt.
     */
    fun forgotPassword(email: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                onSuccess()
            } else {
                onFailure()
            }
        }
    }

    /**
     * Changes the password for the currently authenticated user.
     *
     * This function re-authenticates the user with their old password and then updates the password to the new one.
     * It takes two callback functions: `onSuccess` which is invoked if the password is successfully updated, and
     * `onFailure` which is invoked if there is an error during the re-authentication or password update process.
     * The error message from the failure is passed to the `onFailure` callback.
     *
     * ---
     *
     * Ändert das Passwort des aktuell authentifizierten Benutzers.
     *
     * Diese Funktion führt eine Re-Authentifizierung des Benutzers mit dem alten Passwort durch und aktualisiert anschließend
     * das Passwort auf das neue. Es werden zwei Callback-Funktionen übergeben: `onSuccess`, die aufgerufen wird, wenn das Passwort
     * erfolgreich aktualisiert wurde, und `onFailure`, die aufgerufen wird, wenn ein Fehler während der Re-Authentifizierung oder
     * der Passwortaktualisierung auftritt. Die Fehlermeldung vom Fehler wird an den `onFailure`-Callback übergeben.
     *
     * @param email String: Die E-Mail-Adresse des Benutzers, der das Passwort ändern möchte.
     * @param passwordOld String: Das aktuelle Passwort des Benutzers, das zur Re-Authentifizierung verwendet wird.
     * @param passwordNew String: Das neue Passwort, das auf das alte Passwort aktualisiert werden soll.
     * @param onSuccess () -> Unit: Die Callback-Funktion, die aufgerufen wird, wenn das Passwort erfolgreich aktualisiert wurde.
     * @param onFailure (String) -> Unit: Die Callback-Funktion, die aufgerufen wird, wenn beim Ändern des Passworts ein Fehler auftritt.
     *                                  Die Fehlermeldung wird als String übergeben.
     */
    fun changePassword(email: String, passwordOld: String, passwordNew: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val user = Firebase.auth.currentUser!!
        user.let {
            val credential = EmailAuthProvider.getCredential(email, passwordOld)
            it.reauthenticate(credential)
                .addOnCompleteListener { reauthentication ->
                    if (reauthentication.isSuccessful) {
                        Log.d("Firebase", "User re-authenticated.")
                        it.updatePassword(passwordNew)
                            .addOnCompleteListener { passwordUpdate ->
                                if (passwordUpdate.isSuccessful) {
                                    Log.d("Firebase", "User password updated: $passwordNew")
                                    onSuccess()
                                } else {
                                    val errorMessage = passwordUpdate.exception?.localizedMessage
                                        ?: "Error updating the password"
                                    onFailure(errorMessage)
                                }
                            }
                    } else {
                        val errorMessage = reauthentication.exception?.localizedMessage
                            ?: "Current password is wrong"
                        onFailure(errorMessage)
                    }
                }
        }
    }

    /**
     * Toggles the visibility of a WebView component and updates the attachment path.
     *
     * This function checks the current visibility status of the WebView. If the WebView is currently visible,
     * it hides the WebView and clears the attachment path. If the WebView is not visible, it makes the WebView
     * visible and sets the attachment path to the provided URL.
     *
     * ---
     *
     * Schaltet die Sichtbarkeit einer WebView-Komponente um und aktualisiert den Anhangspfad.
     *
     * Diese Funktion prüft den aktuellen Sichtbarkeitsstatus der WebView. Wenn die WebView derzeit sichtbar ist,
     * wird die WebView ausgeblendet und der Anhangspfad wird gelöscht. Wenn die WebView nicht sichtbar ist,
     * wird die WebView sichtbar gemacht und der Anhangspfad auf die bereitgestellte URL gesetzt.
     *
     * @param url String: Die URL, die im WebView angezeigt werden soll, wenn die WebView sichtbar gemacht wird.
     */
    fun changeWebViewVisibility(url:String){
        if (_isWebViewVisible.value == true){
            _isWebViewVisible.value = false
            _attachmentPath.value = ""
        }else{
            _isWebViewVisible.value = true
            _attachmentPath.value = url
        }
    }

}
