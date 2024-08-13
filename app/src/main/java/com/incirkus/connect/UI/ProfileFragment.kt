package com.incirkus.connect.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.incirkus.connect.ADAPTER.SearchAdapter
import com.incirkus.connect.DATA.Model.ChatParticipants
import com.incirkus.connect.DATA.Model.ChatRoom
import com.incirkus.connect.DATA.Model.Message
import com.incirkus.connect.DATA.Model.User
import com.incirkus.connect.R
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.FragmentProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = loadCurrentUser()
        loadUserList()

        binding.btnNewChatRoom.setOnClickListener {
            viewModel.userList.observe(viewLifecycleOwner){
                viewModel.createNewChatroom(currentUser,it.first())
            }
        }







//        val id: Long = 1L
//
//        viewModel.getChatRoom(id)
//        viewModel.getChatParticipants(id)


//        val currentChatRoom = viewModel.currentChatRoom
//        val currentChatParticipants = viewModel.currentChatParticipants
//
//        binding.btnNewChatRoom.setOnClickListener {
//            viewModel.loadCurrentUser2()
//            if (viewModel.currentUser != null){
//                if (viewModel.userList.value.isNullOrEmpty()){
//                    Log.e("ConnectTag", "currentUser ist Null or Empty")
//                }else{
//
//            viewModel.currentUser.observe(viewLifecycleOwner){currentUser2->
//                viewModel.userList.observe(viewLifecycleOwner){userList2->
//                    if (currentUser2 != null) {
//                        viewModel.createNewChatroom(currentUser2, userList2.first())
//                    }
//                }
//            }
//
//
////                    viewModel.userList.observe(viewLifecycleOwner){
////                        viewModel.createNewChatroom(viewModel.currentUser, it.first())
////                    }
//                }
//            }else{
//                Log.e("ConnectTag", "currentUser ist Null")
//            }
//        }
//
//        binding.btnInsertUser.setOnClickListener {
//
//            val user: User = User(
//                firstName = "Muhammet",
//                lastName = "Incirkus",
//                fullName = "Mammut Incirbird",
//                image= R.drawable.brad,
//                email= "muhammet@incirkus.com",
//                phoneNumber= "+49 1511 1163781",
//                password= "sagichnicht",
//                departmentId= "privat",
//            )
//            viewModel.newUser(user)
//        }
//
//        binding.btnNewChatRoom2.setOnClickListener {
//
//            val chatRoom: ChatRoom = ChatRoom(
//                chatRoomName = "Das ist ein Test",
//                lastMessage = "Testnachricht",
//                lastActivityTimestamp = System.currentTimeMillis(),
//                chatParticipantsId = 0L
//            )
//            viewModel.createNewChatroom2(chatRoom)
//        }
//
//        binding.btnSendNewMessage.setOnClickListener {
//            val message = currentChatRoom.value?.let { it1 ->
//                currentChatParticipants.value?.let { it2 ->
//                    Message(
//                        chatRoomId = it1.chatRoomId,
//                        senderId = it2.user1Id,
//                        messageText = "Hallo!",
//                        timestamp = System.currentTimeMillis(),
//                        messageStatus = "send"
//                    )
//                }
//            }
//            viewModel.sendMessage(message!!)
//        }
//
//        binding.btnSendNewMessage2.setOnClickListener {
//            val message = currentChatRoom.value?.let { it1 ->
//                currentChatParticipants.value?.let { it2 ->
//                    Message(
//                        chatRoomId = it1.chatRoomId,
//                        senderId = it2.user2Id,
//                        messageText = "Wie geht´s?",
//                        timestamp = System.currentTimeMillis(),
//                        messageStatus = "send"
//                    )
//                }
//            }
//            viewModel.sendMessage(message!!)
//        }

    }


    fun loadCurrentUser(): User {
        lateinit var user: User
        user = viewModel.currentUser.value!!
        return user
    }

    fun loadUserList(){
        if (viewModel.userList.value?.isEmpty() == true){
            Log.e("ConnectTag", "userList im viewModel ist Empty")
        }else{
            Log.e("ConnectTag", "userList im viewModel ist NICHT Empty")
        }
    }



}
