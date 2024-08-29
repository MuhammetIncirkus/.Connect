package com.incirkus.connect.ADAPTER

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.incirkus.connect.DATA.Model.Attachment
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.AttachmentListElementBinding

class AttachmentAdapter (private var attachmentList: List<Attachment>,private val viewModel: ViewModel) : RecyclerView.Adapter<AttachmentAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: AttachmentListElementBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = AttachmentListElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return attachmentList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val binding = holder.binding
        val attachment = attachmentList[position]

        binding.tvAttachmentName.text = attachment.attachmentName


        if (attachment.senderID == viewModel.currentUser.value!!.userId){
            binding.tvAttachmentType.text = "You Send"
        }else{
            binding.tvAttachmentType.text = "${viewModel.currentChatPartner.firstName} Send"
        }
        val date = attachment.timestamp?.let { viewModel.convertTimestampToDate(it) }
        binding.tvAttachmentDate.text = date
        binding.ivAttachmentTypeImage.isVisible = false


    }
}