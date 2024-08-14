package com.incirkus.connect.ADAPTER

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.incirkus.connect.DATA.Model.ChatRoom
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.ChatListElementBinding

class CalendarAdapter (private var monthList: List<String>, private val viewModel: ViewModel) : RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: ChatListElementBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ChatListElementBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return monthList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val binding = holder.binding
        val chatRoom = monthList[position]

    }
}