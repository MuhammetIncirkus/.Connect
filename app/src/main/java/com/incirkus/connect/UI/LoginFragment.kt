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


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: ViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {

            val email: String = binding.tvEmail.text.toString()
            val password: String = binding.tvPassword.text.toString()

            if (email != ""){
                if (password != ""){
                    viewModel.login(email, password)
                }else{
                    //TODO Popup bitte Passwort eingeben
                }
            }else{
                //TODO Popup bitte E-Mail Adresse eingeben
            }
        }

        viewModel.currentFirebaseUser.observe(viewLifecycleOwner){
            if (it != null){
                Log.i("Firebase", "LoginFragment: CurrentFirebaseUser: ${it.toString()}")
                findNavController().navigate(R.id.chatsFragment)
                viewModel.loadData()
            }

        }

        binding.tvPasswordForgotton.setOnClickListener {
            findNavController().navigate(R.id.passwordForgottenFragment)
        }

    }
}