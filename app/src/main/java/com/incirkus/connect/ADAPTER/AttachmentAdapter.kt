package com.incirkus.connect.ADAPTER

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
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


        if (attachment.attachmentName!!.contains("document")){
            binding.ivAttachmentTypeImage.load("https://firebasestorage.googleapis.com/v0/b/connect-5cd46.appspot.com/o/Test%2FNo-Image-Placeholder.svg.png?alt=media&token=b2150d39-c00a-4a07-b0d9-4f73fc0f36cc"){
                transformations(RoundedCornersTransformation(topLeft = 14f, bottomRight = 14f, topRight = 14f, bottomLeft = 14f))
            }
        }else{
            binding.ivAttachmentTypeImage.load(attachment.path){
                transformations(RoundedCornersTransformation(topLeft = 14f, bottomRight = 14f, topRight = 14f, bottomLeft = 14f))
            }
        }

        binding.root.setOnClickListener {
            if (attachment.attachmentName!!.contains("image:")){
                viewModel.changeWebViewVisibility(attachment.path!!)
            }
        }


    }
}