package com.incirkus.connect.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.incirkus.connect.ADAPTER.ChatAdapter
import com.incirkus.connect.DATA.Model.ChatRoom
import com.incirkus.connect.DATA.Model.User
import com.incirkus.connect.R
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.FragmentChatsBinding
import com.incirkus.connect.databinding.FragmentSearchBinding


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

//        binding.btnLogout.setOnClickListener {
//
//            viewModel.logout()
//            findNavController().navigate(R.id.loginFragment)
//        }

//        if (viewModel.firebaseChatRoomList != emptyList<ChatRoom>()){
//            binding.tvLoggedUserInfo.text = viewModel.currentFirebaseUser.value?.uid
//            binding.tvLoggedUserInfo.isGone = false
//        }else{
//            binding.tvLoggedUserInfo.isGone = true
//        }

        viewModel.firebaseChatRoomList.observe(viewLifecycleOwner){

            binding.rvChatsFragment.setHasFixedSize(true)
            val chatAdapter = ChatAdapter(it, viewModel)
            binding.rvChatsFragment.adapter = chatAdapter

        }




    }



}