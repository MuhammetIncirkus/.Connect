package com.incirkus.customcalendar.adapter


import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.incirkus.connect.DATA.Model.Day
import com.incirkus.connect.R
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.DayListItemBinding

import java.time.LocalDate


class MonthAdapter (private var dayList: List<Day>, private val viewModel: ViewModel) : RecyclerView.Adapter<MonthAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: DayListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = DayListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val binding = holder.binding
        val day = dayList[position]

        if (day.day != 0){
            binding.tvDayNumber.text = day.day.toString()
        } else{
            binding.root.isVisible = false
        }

        val currentDate = LocalDate.now()
        val context = binding.mcvDay.context
        if (day.year == currentDate.year && day.month == currentDate.month.value && day.day == currentDate.dayOfMonth){
            binding.tvDayNumber.setTypeface(binding.tvDayNumber.getTypeface(), Typeface.BOLD)
            binding.mcvDay.setStrokeColor(ContextCompat.getColor(context, R.color.green))
            binding.mcvDay.strokeWidth = 5

        }


        val holidayList = viewModel.holidayList

        if (holidayList != null) {
            for (holiday in holidayList){
                if (day.year == holiday.holidayYear && day.month == holiday.holidayMonth && day.day == holiday.holidayDay){

                    binding.mcvDay.setStrokeColor(ContextCompat.getColor(context, R.color.red))
                    binding.mcvDay.strokeWidth = 5
                }
            }
        }

    }
}