package com.incirkus.connect.ADAPTER

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
            }
            binding.tvContactName.text = chatPartner.fullName
            binding.tvContactLastMessage.text = chatRoom.lastMessage
            val date = chatRoom.lastActivityTimestamp?.let { viewModel.convertTimestampToDate(it) }
            binding.tvContactLastMessageDate.text = date
            binding.imageView2.isVisible = false

        }


        binding.root.setOnClickListener {
            viewModel.setCurrentChatroom(chatRoom)
            binding.root.findNavController().navigate(R.id.messagesFragment)
        }
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

//        }



    }


//    fun getChatPartnerID(chatRoom: ChatRoom): String?{
//        var chatPartnerID: String? = ""
//
//        for (id in chatRoom.chatParticipants){
//            if (id != viewModel.currentUser.value?.userId){
//                chatPartnerID = id
//            }
//        }
//        return chatPartnerID
//    }
//
//    fun getChatPartner(id: String): User{
//
//        var chatPartner: User = User()
//
//        for (oneUser in viewModel.firebaseUserList.value!!){
//            if (oneUser.userId == id)
//                chatPartner = oneUser
//        }
//
//        return chatPartner
//    }

}