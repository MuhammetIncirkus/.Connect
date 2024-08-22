package com.incirkus.connect.ADAPTER

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.incirkus.connect.DATA.Model.Message
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.IncomingMessageBinding
import com.incirkus.connect.databinding.OutgoingMessageBinding

class MessageAdapter (private var messages: List<Message>, viewModel: ViewModel) : RecyclerView.Adapter<ViewHolder>(){

    inner class IncomingViewHolder (val incomingBinding: IncomingMessageBinding): ViewHolder(incomingBinding.root)
    inner class OutgoingViewHolder (val outgoingBinding: OutgoingMessageBinding): ViewHolder(outgoingBinding.root)

    private val incomingType = 1
    private val outgoingType = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == incomingType){
            val incomingBinding = IncomingMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return IncomingViewHolder(incomingBinding)
        }
        val outgoingBinding = OutgoingMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OutgoingViewHolder(outgoingBinding)

    }

    override fun getItemCount(): Int {

        return messages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val message = messages[position]
        if (holder is IncomingViewHolder){
            //holder.incomingBinding.tvMessageIn.text = message.text
        }else if (holder is OutgoingViewHolder){
            //holder.outgoingBinding.tvMessageOut.text = message.text
        }



    }

//    override fun getItemViewType(position: Int): Int {
//
//        val myUserID = viewModel.
//        val message = messages[position]
//        if (message.incoming){
//            return incomingType
//        }
//        return outgoingType
//    }
}