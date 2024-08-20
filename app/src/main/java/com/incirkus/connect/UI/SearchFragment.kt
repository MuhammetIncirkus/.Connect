package com.incirkus.connect.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.incirkus.connect.ADAPTER.CalendarWeekAdapter
import com.incirkus.connect.ADAPTER.SearchAdapter
import com.incirkus.connect.R
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //viewModel.getUserListWithOutCurrentUser(viewModel.currentUser.value)
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserListFromFirebase()

        val userList = viewModel.firebaseUserList


        viewModel.firebaseUserList.observe(viewLifecycleOwner){

            binding.rvSearchFragment.setHasFixedSize(true)
            val searchAdapter = SearchAdapter(it, viewModel)
            binding.rvSearchFragment.adapter = searchAdapter

        }




    }

}