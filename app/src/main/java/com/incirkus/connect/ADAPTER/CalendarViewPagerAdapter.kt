package com.incirkus.connect.ADAPTER

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.incirkus.connect.UI.CalendarFragment
import com.incirkus.connect.UI.HolidayFragment

class CalendarViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 ->{
                CalendarFragment()
            }
            1 ->{
                HolidayFragment()
            }
            else ->{
                CalendarFragment()
            }
        }
    }
}