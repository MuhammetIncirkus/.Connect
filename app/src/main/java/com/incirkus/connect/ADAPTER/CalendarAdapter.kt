package com.incirkus.customcalendar.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.incirkus.connect.R
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.CalendarListItemBinding

import com.incirkus.customcalendar.adapter.data.Model.Month

import java.time.LocalDate

class CalendarAdapter (private var monthList: List<Month>, private val viewModel: ViewModel,) : RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: CalendarListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = CalendarListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return monthList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val binding = holder.binding
        val month = monthList[position]

        binding.tvMonthAndYear.text = "${month.monthString} ${month.year}"
        val daylist = viewModel.createDayList(month)

        val currentDate = LocalDate.now()

        val context = binding.tvMonthAndYear.context
        if (month.year == currentDate.year && month.monthNumber == currentDate.month.value){
            binding.tvMonthAndYear.setTextColor(ContextCompat.getColor(context, R.color.green))
            binding.tvMonthAndYear.setTypeface(binding.tvMonthAndYear.getTypeface(), Typeface.BOLD)
            binding.tvMonthAndYear.textSize = 20F
            binding.tvMonthAndYear.setShadowLayer(1.5f, 2f, 2f, R.color.black)
        }

        binding.rvDays.setHasFixedSize(true)
        val monthAdapter = MonthAdapter(daylist, viewModel)
        binding.rvDays.adapter = monthAdapter







    }
}