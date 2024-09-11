package com.incirkus.connect.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.incirkus.connect.ADAPTER.ChatAdapter
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.FragmentChatsBinding

class ChatsFragment : Fragment() {

    private lateinit var binding: FragmentChatsBinding
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.firebaseChatRoomList.observe(viewLifecycleOwner){

            binding.rvChatsFragment.setHasFixedSize(true)
            val chatAdapter = ChatAdapter(it.filter { it.lastMessage?.isNotEmpty() == true }, viewModel)
            binding.rvChatsFragment.adapter = chatAdapter

        }

    }

}