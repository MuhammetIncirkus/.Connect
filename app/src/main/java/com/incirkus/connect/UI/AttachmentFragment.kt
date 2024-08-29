package com.incirkus.connect.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.incirkus.connect.ADAPTER.AttachmentAdapter
import com.incirkus.connect.ADAPTER.LeaveRequestAdapter
import com.incirkus.connect.R
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.FragmentAttachmentBinding
import com.incirkus.connect.databinding.FragmentMessagesBinding

class AttachmentFragment : Fragment() {

    private lateinit var binding: FragmentAttachmentBinding
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAttachmentBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.firebaseAttachmentList.observe(viewLifecycleOwner){ it ->
            binding.rvAttachments.setHasFixedSize(true)

            val attachmentAdapter = AttachmentAdapter(it.filter { it.chatRoomId == viewModel.currentChatRoom.value!!.chatRoomId},viewModel)
            binding.rvAttachments.adapter = attachmentAdapter
        }

    }

}