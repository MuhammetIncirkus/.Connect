package com.incirkus.connect.UI

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.incirkus.connect.DATA.Model.User
import com.incirkus.connect.R
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ViewModel by activityViewModels()

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()){
        if(it != null){
            viewModel.uploadImage(it)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentUser.observe(viewLifecycleOwner){

            if (it != null) {
                binding.ivProfilePicture.load(it.image)
                binding.tietName.setText(it.fullName)
                binding.tietDepartment.setText(it.department)
                binding.tietEmail.setText(it.email)
                binding.tietNumber.setText(it.phoneNumber)
            }
        }

        binding.btnLogout.setOnClickListener {

            viewModel.logout()
            findNavController().navigate(R.id.loginFragment)
        }

        binding.btnChangeProfilePicture.setOnClickListener {
            getContent.launch("image/*")
        }





    }

}
