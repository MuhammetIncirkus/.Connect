package com.incirkus.connect.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.incirkus.connect.ADAPTER.ViewPager2Adapter
import com.incirkus.connect.DATA.Model.ChatRoom
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.FragmentViewPageBinding

class ViewPageFragment : Fragment() {

    private lateinit var binding: FragmentViewPageBinding
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewPageBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            val adapter = ViewPager2Adapter(childFragmentManager,lifecycle)
            binding.viewPager.adapter = adapter
            // TabLayoutMediator wird verwendet, um TabLayout und ViewPager2 zu verbinden
            TabLayoutMediator(binding.tabBar, binding.viewPager) { tab, position ->
                when(position){
                    0->{
                        tab.text = "Message"
                    }
                    1->{
                        tab.text = "Information"
                    }
                    2->{
                        tab.text = "Attachments"
                    }
                }
            }.attach()





    }

}