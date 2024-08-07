package com.incirkus.connect.DATA.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.incirkus.connect.DATA.Model.Attachment
import com.incirkus.connect.DATA.Model.Contact
import com.incirkus.connect.DATA.Model.Message

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact)

    @Update
    suspend fun update(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("SELECT * FROM contacts_table")
    fun getAllItems(): LiveData<List<Contact>>

}

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: Message)

    @Update
    suspend fun update(message: Message)

    @Delete
    suspend fun delete(message: Message)

    @Query("SELECT * FROM messages_table")
    fun getAllItems(): LiveData<List<Message>>

}

@Dao
interface AttachmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(attachment: Attachment)

    @Update
    suspend fun update(attachment: Attachment)

    @Delete
    suspend fun delete(attachment: Attachment)

    @Query("SELECT * FROM attachment_table")
    fun getAllItems(): LiveData<List<Attachment>>

}
