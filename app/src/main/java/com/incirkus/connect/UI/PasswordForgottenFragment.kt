package com.incirkus.connect.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.incirkus.connect.R
import com.incirkus.connect.ViewModel
import com.incirkus.connect.databinding.FragmentLoginBinding
import com.incirkus.connect.databinding.FragmentPasswordForgottenBinding

class PasswordForgottenFragment : Fragment() {

    private lateinit var binding: FragmentPasswordForgottenBinding
    private val viewModel: ViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPasswordForgottenBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSendEmail.setOnClickListener {

            val email: String = binding.tvEmail.text.toString()
            if (email != "") {
                viewModel.forgotPassword(email)

                findNavController().navigate(R.id.loginFragment)

            } else {
                //TODO Popup bitte Passwort eingeben
            }
        }
    }
}