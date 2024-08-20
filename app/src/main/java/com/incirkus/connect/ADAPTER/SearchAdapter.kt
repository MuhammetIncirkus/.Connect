package com.incirkus.connect.ADAPTER

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.incirkus.connect.DATA.Model.User
import com.incirkus.connect.DATA.Model.Message
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
        val contact = userList[position]

        //viewModel.filterMessageList(contact.id)

        //binding.ivProfilePicture.setImageResource(contact.image)
        binding.tvContactName.text = contact.fullName
        binding.tvContactLastMessage.text = ""
        binding.tvContactLastMessageDate.text = ""
        binding.imageView2.isVisible = false



        }




    }


