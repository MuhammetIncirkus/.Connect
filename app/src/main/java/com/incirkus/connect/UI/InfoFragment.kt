package com.incirkus.connect.UI

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.incirkus.connect.R
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.FragmentAttachmentBinding
import com.incirkus.connect.databinding.FragmentInfoBinding


class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInfoBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tietName.setText(viewModel.currentChatPartner.fullName)
        binding.tietDepartment.setText(viewModel.currentChatPartner.department)
        binding.tietEmail.setText(viewModel.currentChatPartner.email)
        binding.tietNumber.setText(viewModel.currentChatPartner.phoneNumber)
        binding.ivProfilePicture.load(viewModel.currentChatPartner.image){
            transformations(RoundedCornersTransformation(topLeft = 50f, bottomRight = 50f, topRight = 120f, bottomLeft = 120f))
        }

        binding.btnCall.setOnClickListener {
            val phoneNumber = viewModel.currentChatPartner.phoneNumber!!
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phoneNumber")
            ContextCompat.startActivity(it.context, intent, null)
        }

    }
}