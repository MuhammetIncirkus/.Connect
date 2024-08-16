package com.incirkus.connect.DATA.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "holiday")
data class Holiday(
    @PrimaryKey(autoGenerate = true)
    val holidayId: Long = 0,
    val holidayName: String,
    val holidayRegion: String,
    val holidayDay: Int,
    val holidayMonth: Int,
    val holidayYear: Int,
    val comment: String,
    val augsburg: Boolean,
    val katholisch: Boolean,
    )