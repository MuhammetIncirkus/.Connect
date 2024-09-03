package com.incirkus.connect.ADAPTER

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.incirkus.connect.DATA.Model.ChatRoom
import com.incirkus.connect.DATA.Model.LeaveRequest
import com.incirkus.connect.DATA.Model.User
import com.incirkus.connect.R
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.ChatListElementBinding
import com.incirkus.connect.databinding.LeaveRequestListElementBinding

class LeaveRequestAdapter (private var leaveRequestList: List<LeaveRequest>, private val viewModel: ViewModel) : RecyclerView.Adapter<LeaveRequestAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: LeaveRequestListElementBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = LeaveRequestListElementBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return leaveRequestList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val binding = holder.binding
        val leaveRequest = leaveRequestList[position]

        val startDate =  viewModel.convertTimestampToDates(leaveRequest.startDate!!)
        val endDate = viewModel.convertTimestampToDates(leaveRequest.endDate!! )

        binding.tvContactName.text = "$startDate - $endDate"
        binding.tvContactLastMessage.text = "Status: ${leaveRequest.status}"

    }
}