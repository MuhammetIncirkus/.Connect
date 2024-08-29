package com.incirkus.connect.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.incirkus.connect.ADAPTER.CalendarViewPagerAdapter
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.FragmentViewPageCalendarBinding

class ViewPageCalendarFragment : Fragment() {

    private lateinit var binding: FragmentViewPageCalendarBinding
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewPageCalendarBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CalendarViewPagerAdapter(childFragmentManager,lifecycle)
        binding.viewPagerCalendar.adapter = adapter
        // TabLayoutMediator wird verwendet, um TabLayout und ViewPager2 zu verbinden
        TabLayoutMediator(binding.tabBarCalendar, binding.viewPagerCalendar) { tab, position ->
            when(position){
                0->{
                    tab.text = "Calendar"
                }
                1->{
                    tab.text = "Holidays"
                }
            }
        }.attach()

    }

}