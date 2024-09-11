package com.incirkus.connect.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.incirkus.connect.ADAPTER.LeaveRequestAdapter
import com.incirkus.connect.DATA.Model.LeaveRequest
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.FragmentHolidayBinding
import java.util.UUID

class HolidayFragment : Fragment() {

    private lateinit var binding: FragmentHolidayBinding
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHolidayBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.firebaseLeaveRequestList.observe(viewLifecycleOwner){
            binding.rvLeaveRequest.setHasFixedSize(true)
            val leaveRequestAdapter = LeaveRequestAdapter(it, viewModel) //TODO: ChatAdapter gegen LeaveRequestAdapter austauschen
            binding.rvLeaveRequest.adapter = leaveRequestAdapter
        }

        binding.btnAddLeaveRequest.setOnClickListener {
            datePicker()
        }

    }

    private fun datePicker() {
        // Creating a MaterialDatePicker builder for selecting a date range
        val builder = MaterialDatePicker.Builder.dateRangePicker()

        builder.setTitleText("Select Start and End Date")

        // Building the date picker dialog
        val datePicker = builder.build()
        datePicker.addOnPositiveButtonClickListener { it ->
            // Retrieving the selected start and end dates
            val startDate = it.first
            val endDate = it.second

            val requestId = UUID.randomUUID().toString()
            val userId = viewModel.currentUser.value?.userId
            val status: String? = "open"

            val leaveRequest = LeaveRequest(
                requestId = requestId,
                userId = userId,
                startDate = startDate,
                endDate = endDate,
                status = status,
                )
            viewModel.submitLeaveRequest(leaveRequest)
        }
        // Showing the date picker dialog
        datePicker.show(childFragmentManager, "DATE_PICKER")
    }

}