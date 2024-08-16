package com.incirkus.connect.ADAPTER


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.CalenderweekListItemBinding


class CalendarWeekAdapter (private var dayList: List<Int>, private val viewModel: ViewModel) : RecyclerView.Adapter<CalendarWeekAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: CalenderweekListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = CalenderweekListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val binding = holder.binding
        val day = dayList[position]

        if (day != 0){
            binding.tvDayNumber.text = day.toString()
        }

        }

    }
