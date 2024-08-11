package com.incirkus.connect.DATA.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.incirkus.connect.DATA.Model.Attachment
import com.incirkus.connect.DATA.Model.ChatParticipants
import com.incirkus.connect.DATA.Model.ChatRoom
import com.incirkus.connect.DATA.Model.Department
import com.incirkus.connect.DATA.Model.Holiday
import com.incirkus.connect.DATA.Model.LeaveRequest
import com.incirkus.connect.DATA.Model.Message
import com.incirkus.connect.DATA.Model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM users WHERE userId != :userID")
    fun getAllContacts(userID: Long): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE userId = :userID")
    fun getloggedInUser(userID: Long): User

    @Query("SELECT * FROM users WHERE userId = :userID")
    fun getOneUser(userID: Long): LiveData<User>

}

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: Message)

    @Update
    suspend fun update(message: Message)

    @Delete
    suspend fun delete(message: Message)

    @Query("SELECT * FROM messages")
    fun getAllItems(): LiveData<List<Message>>

    @Query("SELECT * FROM messages WHERE chatRoomId = :chatRoomId" )
    fun getChatRoomMessageList(chatRoomId: String): LiveData<List<Message>>

}



@Dao
interface ChatRoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chat: ChatRoom): Long

    @Update
    suspend fun update(chat: ChatRoom)

    @Delete
    suspend fun delete(chat: ChatRoom)

    @Query("SELECT * FROM chat_rooms")
    fun getAllItems(): LiveData<List<ChatRoom>>

    @Query("SELECT * FROM chat_rooms WHERE chatRoomId = :chatRoomId")
    fun getChatRoomWithID(chatRoomId: Long): ChatRoom

    @Query("SELECT * FROM chat_rooms WHERE chatRoomName = :chatRoomName")
    fun getChatRoomWithName(chatRoomName: String): ChatRoom

}

@Dao
interface HolidayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(holiday: Holiday)

    @Update
    suspend fun update(holiday: Holiday)

    @Delete
    suspend fun delete(holiday: Holiday)

    @Query("SELECT * FROM holiday")
    fun getAllItems(): LiveData<List<Holiday>>

}

@Dao
interface LeaveRequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(leaveRequest: LeaveRequest)

    @Update
    suspend fun update(leaveRequest: LeaveRequest)

    @Delete
    suspend fun delete(leaveRequest: LeaveRequest)

    @Query("SELECT * FROM leave_requests")
    fun getAllItems(): LiveData<List<LeaveRequest>>

}

@Dao
interface DepartmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(department: Department)

    @Update
    suspend fun update(department: Department)

    @Delete
    suspend fun delete(department: Department)

    @Query("SELECT * FROM department")
    fun getAllItems(): LiveData<List<Department>>

}

@Dao
interface ChatParticipantsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chatParticipants: ChatParticipants): Long

    @Update
    suspend fun update(chatParticipants: ChatParticipants)

    @Delete
    suspend fun delete(chatParticipants: ChatParticipants)

    @Query("SELECT * FROM chat_participants")
    fun getAllItems(): LiveData<List<ChatParticipants>>

    @Query("SELECT * FROM chat_participants WHERE chatRoomId = :chatRoomId")
    fun getChatParticipantsWithChatRoomId(chatRoomId: Long): ChatParticipants

}
