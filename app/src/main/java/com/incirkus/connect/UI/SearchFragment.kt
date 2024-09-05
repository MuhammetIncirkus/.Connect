package com.incirkus.connect.UI

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userSearchList.observe(viewLifecycleOwner) {
            viewModel.searchFilter("") // Initiale Liste ohne Filterung
        }

        viewModel.filteredUserList.observe(viewLifecycleOwner){

            binding.rvSearchFragment.setHasFixedSize(true)
            val searchAdapter = SearchAdapter(it.sortedBy { it.department }, viewModel)
            binding.rvSearchFragment.adapter = searchAdapter

        }


        binding.tietSearchText.addTextChangedListener(object : TextWatcher {

            // Die beforeTextChanged-Funktion wird aufgerufen, bevor sich der Text ändert
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            // Die onTextChanged-Funktion wird während der Änderung aufgerufen
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString()
                viewModel.searchFilter(searchText)
            }

            // Die afterTextChanged-Funktion wird nach der Änderung aufgerufen
            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString()
                viewModel.searchFilter(searchText)
            }
        })

        binding.btnClearText.setOnClickListener {
            binding.tietSearchText.text?.clear()
        }

    }




}