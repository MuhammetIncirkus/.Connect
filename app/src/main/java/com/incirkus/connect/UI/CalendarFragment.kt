package com.incirkus.connect.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.incirkus.connect.R
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.FragmentCalendarBinding
import com.incirkus.connect.databinding.FragmentProfileBinding
import com.incirkus.customcalendar.adapter.CalendarAdapter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale


class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCalendarBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getListForHolidays()

        val currentDate = LocalDate.now()
        val actualMonth = viewModel.actualMonth
        val startposition = viewModel.monthList.indexOf(actualMonth)

        val viewPager : ViewPager2 = binding.vp2Calendar
        val calendarAdapter = CalendarAdapter(viewModel.monthList, viewModel)
        viewPager.adapter = calendarAdapter
        viewPager.setCurrentItem(startposition,false)


//        val calendarAdapter = CalendarAdapter(viewModel.monthList, viewModel)
//        binding.rvCalendar.adapter = calendarAdapter
//        //binding.rvCalendar.scrollToPosition(viewModel.monthList.size - 49)
//        binding.rvCalendar.scrollToPosition(viewModel.monthList.indexOf(viewModel.currentMonth.value))

        viewModel.currentMonth.observe(viewLifecycleOwner){


            binding.tvSelectedDay.text = viewModel.currentMonth.value!!.monthString + " " + viewModel.currentMonth.value!!.year

            if(it.year >= viewModel.monthList.first().year){
                binding.btnYearBack.isClickable = false
            } else{
                binding.btnYearBack.isClickable = true
            }

            if (it.year <=viewModel.monthList.last().year ){
                binding.btnYearNext.isClickable = false
            } else{
                binding.btnYearNext.isClickable = true
            }

            if (it.year >= viewModel.monthList.first().year && it.monthNumber <= viewModel.monthList.first().monthNumber){
                binding.btnMonthBack.isClickable = false
            } else{
                binding.btnMonthBack.isClickable = true
            }

            if (it.year <= viewModel.monthList.last().year && it.monthNumber <= viewModel.monthList.last().monthNumber){
                binding.btnMonthNext.isClickable = false
            } else{
                binding.btnMonthNext.isClickable = true
            }

            binding.btnMonthBack.setOnClickListener {
                viewModel.monthMinus1(viewModel.currentMonth.value!!)
            }
            binding.btnMonthNext.setOnClickListener {
                viewModel.monthPlus1(viewModel.currentMonth.value!!)
            }
            binding.btnYearBack.setOnClickListener {
                viewModel.yearMinus1(viewModel.currentMonth.value!!)
            }
            binding.btnYearNext.setOnClickListener {
                viewModel.yearPlus1(viewModel.currentMonth.value!!)
            }
        }


    }


}