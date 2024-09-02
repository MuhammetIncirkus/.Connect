package com.incirkus.connect.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.FragmentCalendarBinding
import com.incirkus.customcalendar.adapter.CalendarAdapter
import java.time.LocalDate



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


        if (viewModel.holidayList.isEmpty()){

            viewModel.getListForHolidays()
        }

        val currentDate = LocalDate.now()
        val actualMonth = viewModel.actualMonth
        val startposition = viewModel.monthList.indexOf(actualMonth)


        val recyclerView= binding.rvCalendar
        val calendarAdapter = CalendarAdapter(viewModel.monthList, viewModel)
        recyclerView.adapter = calendarAdapter
        recyclerView.scrollToPosition(startposition)


    }


}