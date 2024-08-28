package com.incirkus.connect.ADAPTER

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.incirkus.connect.DATA.Model.ChatRoom
import com.incirkus.connect.DATA.Model.Message
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.InfoItemBinding
import com.incirkus.connect.databinding.MessageItemBinding
import java.util.UUID

class ViewPagerAdapter (private var chatRoomList: List<ChatRoom>, viewModel: ViewModel, private val lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<ViewHolder>(){

    inner class MessageViewHolder (val messageBinding: MessageItemBinding): ViewHolder(messageBinding.root)
    inner class InfoViewHolder (val infoBinding: InfoItemBinding): ViewHolder(infoBinding.root)

    private val messageType = 1
    private val infoType = 2

    val viewModel = viewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == messageType){
            val messageBinding = MessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MessageViewHolder(messageBinding)
        }
        val infoBinding = InfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InfoViewHolder(infoBinding)

    }

    override fun getItemCount(): Int {

        return chatRoomList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val chatRoom = chatRoomList[position]
        if (holder is MessageViewHolder){
            viewModel.firebaseMessageList.observe(lifecycleOwner){
                Log.d("MessageList", it.toString())
                val adapter = MessageAdapter(it.filter { it.chatRoomId == viewModel.currentChatRoom.value!!.chatRoomId },viewModel)
                holder.messageBinding.rvMessageFragment.adapter = adapter
                holder.messageBinding.rvMessageFragment.scrollToPosition(it.filter { it.chatRoomId == viewModel.currentChatRoom.value!!.chatRoomId }.size-1)
            }

            holder.messageBinding.btnSend.setOnClickListener {

                if (holder.messageBinding.tietMessageText.text.toString() != "") {

                    val messageId = UUID.randomUUID().toString()
                    val chatRoomId = viewModel.currentChatRoom.value?.chatRoomId
                    val senderId = viewModel.currentUser.value?.userId
                    val messageText: String = holder.messageBinding.tietMessageText.text.toString()
                    val timestamp: Long = System.currentTimeMillis()
                    var messageStatus: String? = "send"

                    val message = Message(
                        messageId = messageId,
                        chatRoomId = chatRoomId,
                        senderId = senderId,
                        messageText = messageText,
                        timestamp = timestamp,
                        messageStatus = messageStatus,

                        )

                    viewModel.sendMessage(message)
                    viewModel.currentChatRoom.value?.lastMessage = messageText
                    viewModel.currentChatRoom.value?.lastActivityTimestamp = timestamp
                    viewModel.currentChatRoom.value?.lastMessageSenderId = senderId
                    viewModel.updateChatRoom()

                    holder.messageBinding.tietMessageText.text?.clear()
                    holder.messageBinding.rvMessageFragment.scrollToPosition(viewModel.firebaseMessageList.value!!.filter { it.chatRoomId == viewModel.currentChatRoom.value!!.chatRoomId }.size-1)
                }

            }
        }else if (holder is InfoViewHolder){
            holder.infoBinding.tietName.setText(viewModel.currentChatPartner.fullName)
            holder.infoBinding.tietDepartment.setText(viewModel.currentChatPartner.department)
            holder.infoBinding.tietEmail.setText(viewModel.currentChatPartner.email)
            holder.infoBinding.tietNumber.setText(viewModel.currentChatPartner.phoneNumber)
            holder.infoBinding.ivProfilePicture.load(viewModel.currentChatPartner.image)

        }



    }

    override fun getItemViewType(position: Int): Int {



        if (position == 0){
            return messageType
        }
        return infoType
    }
}