package com.incirkus.connect.DATA.Model

data class LeaveRequest(

    val requestId: String = "",
    var userId: String? = "",
    var startDate: Long? = 0,
    var endDate: Long? = 0,
    var status: String? = "",

    )