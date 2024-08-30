package com.incirkus.connect.ADAPTER

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.incirkus.connect.DATA.Model.User
import com.incirkus.connect.DATA.Model.Message
import com.incirkus.connect.R
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.ChatListElementBinding
import kotlinx.coroutines.launch

class SearchAdapter (private var userList: List<User>, private val viewModel: ViewModel) : RecyclerView.Adapter<SearchAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: ChatListElementBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ChatListElementBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val binding = holder.binding
        val user = userList[position]


        binding.ivProfilePicture.load(user.image){
            placeholder(R.drawable.ic_launcher_foreground)
        }
        binding.tvContactName.text = user.fullName
        binding.tvContactLastMessage.text = user.department
        binding.tvContactLastMessageDate.text = ""
        binding.imageView2.isVisible = false

        binding.root.setOnClickListener {

            val chatRoomExists =viewModel.createChatroom(user)

            if (chatRoomExists){
                val bundle = Bundle().apply {
                    putInt("start_position", 1) // 1 entspricht dem zweiten Fragment (Index beginnt bei 0)
                }
                binding.root.findNavController().navigate(R.id.viewPageFragment,bundle)
            }else{
                binding.root.findNavController().navigate(R.id.viewPageFragment)
            }

        }


    }




}