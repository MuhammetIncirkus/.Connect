package com.incirkus.connect.UI

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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

        binding.lavUnlock.visibility = View.GONE
        binding.tilEmail.visibility = View.VISIBLE
        binding.tilPassword.visibility = View.VISIBLE
        binding.tvPasswordForgotton.visibility = View.VISIBLE
        binding.lavProfilePictureUpload.visibility = View.VISIBLE
        binding.btnLogin.visibility = View.VISIBLE

        binding.btnLogin.setOnClickListener {

            if(binding.tvEmail.text.isNullOrEmpty() || binding.tvPassword.text.isNullOrEmpty()){
                var a = AnimationUtils.loadAnimation(context,R.anim.shake)
                binding.tvEmail.startAnimation(a)
                binding.tvPassword.startAnimation(a)
            }

            val email: String = binding.tvEmail.text.toString()
            val password: String = binding.tvPassword.text.toString()

            if (email != ""){
                if (password != ""){
                    viewModel.login(email, password)
                }
            }
        }

        viewModel.currentFirebaseUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                viewModel.loadData()  // Daten vom Server laden
                binding.tilEmail.visibility = View.INVISIBLE
                binding.tilPassword.visibility = View.INVISIBLE
                binding.tvPasswordForgotton.visibility = View.INVISIBLE
                binding.lavProfilePictureUpload.visibility = View.INVISIBLE
                binding.btnLogin.visibility = View.INVISIBLE
                // Animation sichtbar machen und starten
                binding.lavUnlock.visibility = View.VISIBLE

                // Listener für Animation setzen
                binding.lavUnlock.addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {
                        // Hier kannst du optional etwas tun, wenn die Animation startet
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        // Wenn die Animation endet, navigiere zum ChatsFragment
                        Log.i("Firebase", "LoginFragment: Animation fertig, jetzt Daten laden und navigieren")

                        findNavController().navigate(R.id.chatsFragment)  // Navigation zum ChatsFragment
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        // Optional: Wenn die Animation abgebrochen wird
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                        // Optional: Wenn die Animation wiederholt wird
                    }
                })

                binding.lavUnlock.playAnimation()
            }
        }

        binding.tvPasswordForgotton.setOnClickListener {
            findNavController().navigate(R.id.passwordForgottenFragment)
        }

    }
}