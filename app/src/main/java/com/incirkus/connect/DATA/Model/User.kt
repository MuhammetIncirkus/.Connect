package com.incirkus.connect.DATA.Model

data class User(

    var userId: String = "",
    val firstName: String? = "",
    var lastName: String? = "",
    val fullName: String? = "",
    var image: String? = "",
    var email: String? = "",
    var phoneNumber: String? = "",
    var department: String? = "",
)