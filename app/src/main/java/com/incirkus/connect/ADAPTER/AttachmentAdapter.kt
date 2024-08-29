package com.incirkus.connect.ADAPTER

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.incirkus.connect.DATA.Model.Attachment
import com.incirkus.connect.databinding.AttachmentListElementBinding

class AttachmentAdapter (private var attachmentList: List<Attachment>) : RecyclerView.Adapter<AttachmentAdapter.ItemViewHolder>() {
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
        binding.tvAttachmentType.text = attachment.attachmentType
        binding.tvAttachmentDate.text = attachment.timestamp.toString()

    }
}