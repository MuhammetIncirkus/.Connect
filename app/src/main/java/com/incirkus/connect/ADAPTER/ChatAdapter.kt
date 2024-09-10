package com.incirkus.connect.ADAPTER

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.incirkus.connect.DATA.Model.ChatRoom
import com.incirkus.connect.DATA.Model.User
import com.incirkus.connect.R
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.ChatListElementBinding

class ChatAdapter (private var chatRoomList: List<ChatRoom>, private val viewModel: ViewModel) : RecyclerView.Adapter<ChatAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: ChatListElementBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ChatListElementBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return chatRoomList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val binding = holder.binding
        val chatRoom = chatRoomList[position]

        val chatPartnerID = viewModel.getChatPartnerID(chatRoom)
        var chatPartner: User = User()
        if (chatPartnerID != null){
            chatPartner = viewModel.getChatPartner(chatPartnerID)
        }

        if (chatPartner.userId != ""){
            binding.ivProfilePicture.load(chatPartner.image){
                placeholder(R.drawable.ic_launcher_foreground)
                crossfade(true)
                crossfade(300)
            }
            binding.tvContactName.text = chatPartner.fullName
            if (chatRoom.lastMessageSenderId == viewModel.currentFirebaseUser.value?.uid){
                binding.tvContactLastMessage.text = "You: ${chatRoom.lastMessage}"
            }else{
                binding.tvContactLastMessage.text = "${chatPartner.firstName}: ${chatRoom.lastMessage}"
            }
            val date = chatRoom.lastActivityTimestamp?.let { viewModel.convertTimestampToDate(it) }
            if (date == "01.01.1970"){
                binding.tvContactLastMessageDate.text = ""
                binding.tvContactLastMessage.text = "No messages"
            }else{
                binding.tvContactLastMessageDate.text = date
            }


        }


        binding.root.setOnClickListener {
            viewModel.setCurrentChatroom(chatRoom)

            binding.root.findNavController().navigate(R.id.viewPageFragment)
        }

    }

}