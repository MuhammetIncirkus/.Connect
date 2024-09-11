package com.incirkus.connect.ADAPTER

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.incirkus.connect.DATA.Model.Message
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.IncomingMessageBinding
import com.incirkus.connect.databinding.OutgoingMessageBinding

class MessageAdapter (private var messageList: List<Message>, viewModel: ViewModel) : RecyclerView.Adapter<ViewHolder>(){

    inner class IncomingViewHolder (val incomingBinding: IncomingMessageBinding): ViewHolder(incomingBinding.root)
    inner class OutgoingViewHolder (val outgoingBinding: OutgoingMessageBinding): ViewHolder(outgoingBinding.root)

    private val incomingType = 1
    private val outgoingType = 2

    val myUserID = viewModel.currentUser.value?.userId
    val myChatPartner = viewModel.currentChatPartner
    val viewModel = viewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == incomingType){
            val incomingBinding = IncomingMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return IncomingViewHolder(incomingBinding)
        }
        val outgoingBinding = OutgoingMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OutgoingViewHolder(outgoingBinding)

    }

    override fun getItemCount(): Int {

        return messageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val message = messageList[position]
        if (holder is IncomingViewHolder){
            holder.incomingBinding.tvIncomingMessageText.text = message.messageText
            holder.incomingBinding.ivProfilePicture.load(myChatPartner.image)
            val date = message.timestamp?.let { viewModel.convertTimestampToDate(it) }
            holder.incomingBinding.tvIncomingMessageDate.text = date
        }else if (holder is OutgoingViewHolder){
            holder.outgoingBinding.tvIncomingMessageText.text = message.messageText
            val date = message.timestamp?.let { viewModel.convertTimestampToDate(it) }
            holder.outgoingBinding.tvIncomingMessageDate.text = date
        }

    }

    override fun getItemViewType(position: Int): Int {

        val message = messageList[position]
        if (message.senderId != myUserID){
            return incomingType
        }
        return outgoingType
    }
}