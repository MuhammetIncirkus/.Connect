package com.incirkus.connect.DATA.Model

data class APIResponse(
    val status: String= "",
    val feiertage: List<Response> = listOf()
)
