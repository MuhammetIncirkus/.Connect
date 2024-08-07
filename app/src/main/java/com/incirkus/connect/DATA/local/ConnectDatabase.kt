package com.incirkus.connect.DATA.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.incirkus.connect.DATA.Model.Attachment
import com.incirkus.connect.DATA.Model.Contact
import com.incirkus.connect.DATA.Model.Message

@Database(entities = [Contact::class, Message::class, Attachment::class], version = 1)
abstract class ConnectDatabase :RoomDatabase() {

    abstract fun contactDao(): ContactDao
    abstract fun messageDao(): MessageDao
    abstract fun attachmentDao(): AttachmentDao

}

private lateinit var INSTANCE: ConnectDatabase

fun getDataBase(context: Context): ConnectDatabase {
    synchronized(ConnectDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ConnectDatabase::class.java,
                "app_database"
            ).build()
        }
        return INSTANCE
    }
}
