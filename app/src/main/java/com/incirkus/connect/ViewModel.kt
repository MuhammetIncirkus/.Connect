package com.incirkus.connect

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.incirkus.connect.DATA.Model.Message
import com.incirkus.connect.DATA.Model.User
import com.incirkus.connect.DATA.Repository
import com.incirkus.connect.DATA.local.getDataBase
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDataBase(application)
    val repository = Repository(database)
    val currentUser = repository.currentUser
    val userList = repository.userList
//    val loggedUser = repository.loggedUser
//    private var _usersChatMessageList = MutableLiveData<List<Message>> (repository.usersChatMessageList.value)
//    val usersChatMessageList: LiveData<List<Message>> = _usersChatMessageList

    fun preloadItems(){
        viewModelScope.launch {
            repository.preload()
        }
    }
    fun loadCurrentUser(){
        viewModelScope.launch {
            repository.loadCurrentUser()
        }
    }
    fun loadUserList(){
        viewModelScope.launch {
            repository.loadUserList()
        }
    }

    fun createNewChatroom(currentUser: User, selectedUser: User){
        viewModelScope.launch {
            repository.createNewChatroom(currentUser,selectedUser)
        }
    }

//    fun getcurrentChatMessageList() {
//        viewModelScope.launch {
//            repository.getcurrentChatMessageList()
//        }
//
//    }

//    fun filterMessageList(userID: Long){
//        var messageList: MutableList<Message> = mutableListOf()
//
//        viewModelScope.launch {
//            if (_usersChatMessageList.value?.isNotEmpty() == true) {
//
//                for (message in _usersChatMessageList.value!!) {
//                    if (message.senderID == userID || message.recipientID == userID) {
//                        messageList.add(message)
//                    }
//                }
//            }
//            _usersChatMessageList.postValue(messageList)
//        }
//    }




}
