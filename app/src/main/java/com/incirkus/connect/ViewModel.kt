package com.incirkus.connect

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.incirkus.connect.DATA.Model.ChatRoom
import com.incirkus.connect.DATA.Model.Message
import com.incirkus.connect.DATA.Model.User
import com.incirkus.connect.DATA.Repository
import com.incirkus.connect.DATA.local.getDataBase
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDataBase(application)
    val repository = Repository(database,this)

    val currentUser = repository.currentUser
    val userList = repository.userList
    val currentChatRoom = repository.currentChatRoom
    val currentChatParticipants = repository.currentChatParticipants
    val usersChatRoomIdList = repository.usersChatRoomIdList
    val usersChatRoomList = repository.usersChatRoomList


    fun preloadItems(){
        viewModelScope.launch {
            repository.preload()
            repository.loadCurrentUserFromUserList()
            repository.loadCurrentUser()
            //repository.loadUserList()
        }
    }

//    fun loadCurrentUser(): User{
//        lateinit var user: User
//        viewModelScope.launch {
//            user = repository.loadCurrentUserFromUserlist()
//        }
//        return user
//    }

//    fun loadCurrentUser2(){
//        viewModelScope.launch {
//            repository.loadCurrentUser()
//        }
//    }

//    fun loadUserList(){
//        viewModelScope.launch {
//            repository.loadUserList()
//        }
//    }

    fun createNewChatroom(currentUser: User, selectedUser: User){
        viewModelScope.launch {
            repository.createNewChatroom(currentUser,selectedUser)
        }
    }

//    fun createNewChatroom2(chatRoom: ChatRoom){
//        viewModelScope.launch {
//            repository.createNewChatroom2(chatRoom)
//        }
//    }

//    fun newUser(user:User){
//        viewModelScope.launch {
//            repository.newUser(user)
//        }
//    }

    fun getChatRoom(chatRoomId:Long){
        viewModelScope.launch {
            repository.getChatRoom(chatRoomId)
        }
    }

    fun getChatParticipants(chatParticipantsId:Long){
        viewModelScope.launch {
            repository.getChatParticipants(chatParticipantsId)
        }
    }

    fun sendMessage(message: Message){
        viewModelScope.launch {
            try {

                repository.sendMessage(message)
            }catch (e:Exception){
                Log.e("ConnectTag", "ViewModel.sendMessage: Error sending message: ${e.message}")
            }
        }
    }

    fun loadUsersChatLists(){
        viewModelScope.launch {
            repository.loadUsersChatParticipantsLists()
        }
    }

    fun getOneUserById(userID: Long): LiveData<User>{
        lateinit var user: LiveData<User>
        viewModelScope.launch {
            user = repository.getOneUserById(userID)
        }
        return user
    }


}
