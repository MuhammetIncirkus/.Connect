package com.incirkus.connect.DATA.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.incirkus.connect.DATA.Model.Attachment
import com.incirkus.connect.DATA.Model.ChatParticipants
import com.incirkus.connect.DATA.Model.ChatRoom
import com.incirkus.connect.DATA.Model.CurrentUserDatatype
import com.incirkus.connect.DATA.Model.Department
import com.incirkus.connect.DATA.Model.LeaveRequest
import com.incirkus.connect.DATA.Model.Holiday
import com.incirkus.connect.DATA.Model.Message
import com.incirkus.connect.DATA.Model.User

//@Database(entities = [User::class, Message::class, Department::class, LeaveRequest::class, Holiday::class, ChatParticipants::class, CurrentUserDatatype::class], version = 1)
//abstract class ConnectDatabase :RoomDatabase() {
//
//    abstract fun userDao(): UserDao
//    abstract fun messageDao(): MessageDao
//    abstract fun departmentDao(): DepartmentDao
//    abstract fun leaveRequestDao(): LeaveRequestDao
//    abstract fun holidayDao(): HolidayDao
//    //abstract fun chatRoomDao(): ChatRoomDao
//    abstract fun chatParticipantsDao(): ChatParticipantsDao
//    abstract fun currentUserDao(): CurrentUserDao
//
//
//}
//
//private lateinit var INSTANCE: ConnectDatabase
//
//fun getDataBase(context: Context): ConnectDatabase {
//    synchronized(ConnectDatabase::class.java) {
//        if (!::INSTANCE.isInitialized) {
//            INSTANCE = Room.databaseBuilder(
//                context.applicationContext,
//                ConnectDatabase::class.java,
//                "connect_database"
//            ).build()
//        }
//        return INSTANCE
//    }
//}
