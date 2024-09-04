package com.incirkus.customcalendar.adapter


import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.incirkus.connect.ADAPTER.CalendarWeekAdapter
import com.incirkus.connect.ADAPTER.HolidayAdapter
import com.incirkus.connect.DATA.Model.Holiday
import com.incirkus.connect.DATA.Model.Month
import com.incirkus.connect.R
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.CalendarListItemBinding
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


        val currentDate = LocalDate.now()

        var actualMonth: Boolean = false

        Log.d("Firebase", "Calendar Adapter: currentDate.year: ${currentDate.year} month.year: ${month.year}")
        Log.d("Firebase", "Calendar Adapter: currentDate.month.value: ${currentDate.month.value} month.year: ${month.monthNumber}")

        if (month.year == currentDate.year){
            if (month.monthNumber == currentDate.month.value){
                actualMonth = true
            }
        }

        val context = binding.tvMonthAndYear.context
        if (actualMonth){
            binding.tvMonthAndYear.setTextColor(ContextCompat.getColor(context, R.color.new_primary))
            binding.tvMonthAndYear.setTypeface(binding.tvMonthAndYear.getTypeface(), Typeface.BOLD)
            binding.tvMonthAndYear.textSize = 20F
            binding.tvMonthAndYear.setShadowLayer(1.5f, 2f, 2f, R.color.new_secondary)
        }else{
            binding.tvMonthAndYear.setTextColor(ContextCompat.getColor(context, R.color.black))
            binding.tvMonthAndYear.setTypeface(binding.tvMonthAndYear.getTypeface(), Typeface.ITALIC)
            binding.tvMonthAndYear.textSize = 14F
            binding.tvMonthAndYear.setShadowLayer(0f, 0f, 0f, R.color.black)
        }

        binding.rvDays.setHasFixedSize(true)
        val monthAdapter = MonthAdapter(month.daylist, viewModel)
        binding.rvDays.adapter = monthAdapter

        var weeklist: MutableList<Int> = mutableListOf()
        for (day in month.daylist){
            weeklist.add(day.calendarweek)
        }

        weeklist.removeAll { it == 0 }
        var weeklist2= weeklist.distinct()

        binding.rvWeekDays.setHasFixedSize(true)
        val calendarWeekAdapter = CalendarWeekAdapter(weeklist2, viewModel)
        binding.rvWeekDays.adapter = calendarWeekAdapter

        val holidayList = viewModel.holidayList

        var newHolidayList: MutableList<Holiday> = mutableListOf()

        if (holidayList != null) {
            for (holiday in holidayList){
                if (month.year == holiday.holidayYear && month.monthNumber == holiday.holidayMonth){
                    newHolidayList.add(holiday)
                }
            }
        }


        newHolidayList = newHolidayList.distinct().toMutableList()

        binding.rvHolidaysInfo.setHasFixedSize(true)
        val holidayAdapter = HolidayAdapter(newHolidayList)
        binding.rvHolidaysInfo.adapter = holidayAdapter

    }
}