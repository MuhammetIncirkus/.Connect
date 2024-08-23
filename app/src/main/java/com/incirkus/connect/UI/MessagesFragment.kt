package com.incirkus.connect.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.incirkus.connect.ADAPTER.MessageAdapter
import com.incirkus.connect.DATA.Model.Message
import com.incirkus.connect.R
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.FragmentLoginBinding
import com.incirkus.connect.databinding.FragmentMessagesBinding
import java.util.UUID


class MessagesFragment : Fragment() {

    private lateinit var binding: FragmentMessagesBinding
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMessagesBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentUser
        viewModel.currentChatRoom
        viewModel.currentChatPartner





        viewModel.firebaseCurrentMessageList.observe(viewLifecycleOwner){
            Log.d("MessageList", it.toString())
            val adapter = MessageAdapter(it,viewModel)
            binding.rvMessageFragment.adapter = adapter
            binding.rvMessageFragment.scrollToPosition(it.lastIndex)
        }


        binding.btnSendMessage.setOnClickListener {

            val messageId = UUID.randomUUID().toString()
            val chatRoomId = viewModel.currentChatRoom.value?.chatRoomId
            val senderId = viewModel.currentUser.value?.userId
            val messageText: String = "Hallo?"
            val timestamp: Long = System.currentTimeMillis()
            var messageStatus: String? = "send"

            val message = Message(
                messageId = messageId,
                chatRoomId = chatRoomId,
                senderId = senderId,
                messageText = messageText,
                timestamp = timestamp,
                messageStatus = messageStatus
            )

            viewModel.sendMessage(message)
            viewModel.currentChatRoom.value?.lastMessage = messageText
            viewModel.currentChatRoom.value?.lastActivityTimestamp = timestamp
            viewModel.updateChatRoom()


        }

    }

}