package com.incirkus.connect.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.incirkus.connect.ADAPTER.AttachmentAdapter
import com.incirkus.connect.ADAPTER.LeaveRequestAdapter
import com.incirkus.connect.DATA.Model.Attachment
import com.incirkus.connect.R
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.FragmentAttachmentBinding
import com.incirkus.connect.databinding.FragmentMessagesBinding
import java.util.UUID

class AttachmentFragment : Fragment() {

    private lateinit var binding: FragmentAttachmentBinding
    private val viewModel: ViewModel by activityViewModels()

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()){
        if(it != null){

            val attachmentName: String = it.lastPathSegment.toString()
            val attachmentType: String = attachmentName.substringAfterLast(".")
            val senderID: String = viewModel.currentFirebaseUser.value!!.uid
            val chatRoomId: String = viewModel.currentChatRoom.value!!.chatRoomId
            val attachmentId: String = UUID.randomUUID().toString()
            val timestamp: Long = System.currentTimeMillis()

            val attachment: Attachment = Attachment(
                attachmentId = attachmentId,
                senderID = senderID,
                attachmentName = attachmentName,
                attachmentType = attachmentType,
                chatRoomId = chatRoomId,
                timestamp = timestamp
            )
            viewModel.uploadAttachment(it, attachment)
        }
    }

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

        binding.lavProfilePictureUpload.visibility = View.GONE

        viewModel.firebaseAttachmentList.observe(viewLifecycleOwner){ it ->
            binding.rvAttachments.setHasFixedSize(true)

            val attachmentAdapter = AttachmentAdapter(it.filter { it.chatRoomId == viewModel.currentChatRoom.value!!.chatRoomId},viewModel)
            binding.rvAttachments.adapter = attachmentAdapter
        }

        binding.btnSelect.setOnClickListener{
            if (binding.btnSelect.isVisible){
                getContent.launch("*/*")

            }
        }

        viewModel.isUploading.observe(viewLifecycleOwner){
            if (it){
                binding.lavProfilePictureUpload.visibility = View.VISIBLE
            }else{
                binding.lavProfilePictureUpload.visibility = View.GONE
            }
        }

    }

}