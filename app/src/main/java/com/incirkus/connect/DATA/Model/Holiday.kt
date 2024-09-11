package com.incirkus.connect.DATA.Model

data class Holiday(

    val holidayId: String = "",
    val holidayName: String? = "",
    val holidayRegion: String? = "",
    val holidayDay: Int? = 0,
    val holidayMonth: Int? = 0,
    val holidayYear: Int? = 0,
    val comment: String? = "",
    val augsburg: Boolean? = false,
    val katholisch: Boolean? = false,

    )