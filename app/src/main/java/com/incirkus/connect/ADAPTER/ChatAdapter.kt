package com.incirkus.connect.ADAPTER

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.incirkus.connect.DATA.Model.ChatRoom
import com.incirkus.connect.DATA.Model.User
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
        val calendar = chatRoomList[position]

//        val currentUser = viewModel.currentUser
//        //val currentUser = loadCurrentUser()
//        var chatPartner= viewModel.currentUser.value
//
//        if (viewModel.currentChatParticipants.value != null){
//
//        if (currentUser.value!!.userId == viewModel.currentChatParticipants.value!!.user1Id){
//            chatPartner = viewModel.getOneUserById(viewModel.currentChatParticipants.value!!.user2Id).value!!
//        }else  {
//            chatPartner = viewModel.getOneUserById(viewModel.currentChatParticipants.value!!.user1Id).value!!
//        }
//        }



//        lateinit var secondUser: LiveData<User>
//        viewModel.getChatParticipants(chatRoom.chatRoomId)
//
//        if (viewModel.currentUser.value!!.userId == viewModel.currentChatParticipants.value!!.user1Id){
//
//            secondUser = viewModel.getOneUserById(viewModel.currentChatParticipants.value!!.user2Id)
//        }else{
//            secondUser = viewModel.getOneUserById(viewModel.currentChatParticipants.value!!.user1Id)
//        }
//
//        //viewModel.filterMessageList(contact.id)
//        if (secondUser.isInitialized){
//
//        binding.ivProfilePicture.setImageResource(chatPartner!!.image)
//        binding.tvContactName.text = chatPartner!!.fullName
//        binding.tvContactLastMessage.text = chatRoom.lastMessage
//        binding.tvContactLastMessageDate.text = ""
//        binding.imageView2.isVisible = false
//        }



    }

}