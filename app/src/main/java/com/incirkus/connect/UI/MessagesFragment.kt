package com.incirkus.connect.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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



//        viewModel.messageList.observe(viewLifecycleOwner){
//            Log.d("MessageList", it.toString())
//            val adapter = ChatDetailAdapter(it)
//            binding.rvMessages.adapter = adapter
//            binding.rvMessages.scrollToPosition(viewModel.messageList.value!!.lastIndex)
//        }


        binding.root.setOnClickListener {

            val messageId = UUID.randomUUID().toString()
            val chatRoomId = viewModel.currentChatRoom.value?.chatRoomId
            val senderId = viewModel.currentUser.value?.userId
            val messageText: String = ""
            val timestamp: Long = System.currentTimeMillis()
            var messageStatus: String? = "send"
        }

    }

}