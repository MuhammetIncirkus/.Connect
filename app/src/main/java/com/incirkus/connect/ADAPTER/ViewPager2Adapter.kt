package com.incirkus.connect.ADAPTER

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.incirkus.connect.UI.AttachmentFragment
import com.incirkus.connect.UI.InfoFragment
import com.incirkus.connect.UI.MessagesFragment

class ViewPager2Adapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 ->{
                MessagesFragment()
            }
            1 ->{
                InfoFragment()
            }
            2 ->{
                AttachmentFragment()
            }
            else ->{
                MessagesFragment()
            }
        }
    }
}