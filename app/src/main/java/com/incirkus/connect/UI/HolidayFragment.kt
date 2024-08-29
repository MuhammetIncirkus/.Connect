package com.incirkus.connect.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.incirkus.connect.ADAPTER.ChatAdapter
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.FragmentHolidayBinding

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

        viewModel.firebaseChatRoomList.observe(viewLifecycleOwner){
            binding.rvLeaveRequest.setHasFixedSize(true)
            val chatAdapter = ChatAdapter(it, viewModel) //TODO: ChatAdapter gegen LeaveRequestAdapter austauschen
            binding.rvLeaveRequest.adapter = chatAdapter
        }

        binding.btnAddLeaveRequest.setOnClickListener {

        }

    }

}