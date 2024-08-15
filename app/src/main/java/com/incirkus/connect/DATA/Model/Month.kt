package com.incirkus.customcalendar.adapter.data.Model

data class Month(
    var year: Int,
    var monthNumber: Int,
    var monthString: String,
    val monthLength: Int,
    val firstDayOfMontAsWeekdayString: String,
    val firstDayOfMontAsWeekdayInt: Int,

)
