package com.incirkus.connect.DATA.Model

data class Day(
    val day: Int,
    val month: Int,
    val year: Int,
    val weekdayAsString: String,
    val weekdayAsInt: Int,
    val calendarweek: Int
)
