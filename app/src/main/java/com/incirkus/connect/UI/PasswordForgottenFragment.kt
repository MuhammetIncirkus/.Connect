package com.incirkus.connect.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
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

            if(binding.tvEmail.text.isNullOrEmpty()){
                var a = AnimationUtils.loadAnimation(context,R.anim.shake)
                binding.tvEmail.startAnimation(a)
            }

            val email: String = binding.tvEmail.text.toString()
            if (email != "") {
                viewModel.forgotPassword(
                    email,
                    onSuccess = {
                    Toast.makeText(requireContext(), "Email send", Toast.LENGTH_SHORT).show()
                },
                    onFailure = {
                        Toast.makeText(requireContext(), "Error: Email could not be sent", Toast.LENGTH_SHORT).show()
                    }
                )

                findNavController().navigate(R.id.loginFragment)

            } else {
                //TODO Popup bitte Passwort eingeben
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }
}