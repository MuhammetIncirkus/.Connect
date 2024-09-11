package com.incirkus.connect.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mcvHolidayCheckBoxes.visibility = View.GONE
        binding.btnApiChanges.setImageResource(R.drawable.baseline_arrow_drop_down_24)
        binding.lavProfilePictureUpload.visibility = View.GONE
        binding.lavApiUpload.visibility = View.GONE

        val germanStatesList = listOf(
            binding.cbBW,
            binding.cbBY,
            binding.cbBE,
            binding.cbBB,
            binding.cbHB,
            binding.cbHH,
            binding.cbHE,
            binding.cbMV,
            binding.cbNI,
            binding.cbNW,
            binding.cbRP,
            binding.cbSL,
            binding.cbSN,
            binding.cbST,
            binding.cbSH,
            binding.cbTH,
        )

        viewModel.currentUser.observe(viewLifecycleOwner){

            if (it != null) {
                binding.ivProfilePicture.load(it.image){
                    transformations(RoundedCornersTransformation(topLeft = 120f, bottomRight = 120f, topRight = 50f, bottomLeft = 50f))
                    crossfade(true)
                    crossfade(300)
                }
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

        binding.btnChangePassword.setOnClickListener {

            showChangePasswordDialog()

        }

        viewModel.isUploading.observe(viewLifecycleOwner){
            if (it){
                binding.lavProfilePictureUpload.visibility = View.VISIBLE
            }else{
                binding.lavProfilePictureUpload.visibility = View.GONE
            }
        }
        viewModel.isUploadingApi.observe(viewLifecycleOwner){
            if (it){
                binding.lavApiUpload.visibility = View.VISIBLE
            }else{
                binding.lavApiUpload.visibility = View.GONE
            }
        }

        binding.btnApiChanges.setOnClickListener {

            if (binding.mcvHolidayCheckBoxes.visibility == View.GONE){
                binding.mcvHolidayCheckBoxes.visibility = View.VISIBLE
                binding.btnApiChanges.setImageResource(R.drawable.baseline_arrow_drop_up_24)
            }else{
                binding.mcvHolidayCheckBoxes.visibility = View.GONE
                binding.btnApiChanges.setImageResource(R.drawable.baseline_arrow_drop_down_24)
            }
        }
        
        binding.cbGermanywide.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                germanStatesList.forEach { it.isChecked = true }
            }
        }

        germanStatesList.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked){
                    binding.cbGermanywide.isChecked = false
                }
            }
        }

        binding.btnSaveApiChanges.setOnClickListener {
            if (binding.mcvHolidayCheckBoxes.visibility == View.VISIBLE){
                if (binding.cbGermanywide.isChecked){
                    viewModel.getListForHolidaysManual()
                }else{
                    var states: String = ""
                    if (binding.cbBW.isChecked){
                        states = states + ",bw"
                    }
                    if (binding.cbBY.isChecked){
                        states = states + ",by"
                    }
                    if (binding.cbSH.isChecked){
                        states = states + ",sh"
                    }
                    if (binding.cbBE.isChecked){
                        states = states + ",be"
                    }
                    if (binding.cbBB.isChecked){
                        states = states + ",bb"
                    }
                    if (binding.cbHB.isChecked){
                        states = states + ",hb"
                    }
                    if (binding.cbHH.isChecked){
                        states = states + ",hh"
                    }
                    if (binding.cbHE.isChecked){
                        states = states + ",he"
                    }
                    if (binding.cbMV.isChecked){
                        states = states + ",mv"
                    }
                    if (binding.cbNI.isChecked){
                        states = states + ",ni"
                    }
                    if (binding.cbNW.isChecked){
                        states = states + ",nw"
                    }
                    if (binding.cbRP.isChecked){
                        states = states + ",rp"
                    }
                    if (binding.cbSL.isChecked){
                        states = states + ",sl"
                    }
                    if (binding.cbSN.isChecked){
                        states = states + ",sn"
                    }
                    if (binding.cbST.isChecked){
                        states = states + ",st"
                    }
                    if (binding.cbTH.isChecked){
                        states = states + ",th"
                    }
                    if (states.isNotEmpty()){
                    val states2 = states.substring(1)//Das erste Komma (,) wird mit substring entfernt
                    viewModel.getHolidayListForSomeStates(states2)
                    }else{
                            var a = AnimationUtils.loadAnimation(context,R.anim.shake)
                            binding.btnSaveApiChanges.startAnimation(a)
                        binding.cbGermanywide.startAnimation(a)
                        binding.cbBW.startAnimation(a)
                        binding.cbBE.startAnimation(a)
                        binding.cbBB.startAnimation(a)
                        binding.cbHB.startAnimation(a)
                        binding.cbHH.startAnimation(a)
                        binding.cbHE.startAnimation(a)
                        binding.cbMV.startAnimation(a)
                        binding.cbNI.startAnimation(a)
                        binding.cbNW.startAnimation(a)
                        binding.cbRP.startAnimation(a)
                        binding.cbSL.startAnimation(a)
                        binding.cbSN.startAnimation(a)
                        binding.cbST.startAnimation(a)
                        binding.cbBY.startAnimation(a)
                        binding.cbSH.startAnimation(a)
                        binding.cbTH.startAnimation(a)
                    }
                }
            }
        }

    }

    private fun showChangePasswordDialog() {
        // Inflate the dialog layout
        val dialogView = layoutInflater.inflate(R.layout.password_change_popup, null)

        val currentPasswordInput: EditText = dialogView.findViewById(R.id.tiet_oldPassword)
        val newPasswordInput: EditText = dialogView.findViewById(R.id.tiet_newPassword)
        val confirmPasswordInput: EditText = dialogView.findViewById(R.id.tiet_confirmNewPassword)

        // Build and show the dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Change Password")
            .setView(dialogView)
            .setPositiveButton("Confirm") { dialog, _ ->

                // Hier wird der Bestätigungs-Button geklickt
                val currentPassword = currentPasswordInput.text.toString()
                val newPassword = newPasswordInput.text.toString()
                val confirmPassword = confirmPasswordInput.text.toString()

                if (newPassword == confirmPassword) {
                    if (newPassword.length>5){

                        viewModel.changePassword(
                            email = viewModel.currentFirebaseUser.value!!.email!!,
                            passwordOld = currentPassword,
                            passwordNew = newPassword,
                            onSuccess = {
                                Toast.makeText(requireContext(), "Password changed", Toast.LENGTH_SHORT).show()
                            },
                            onFailure = { errorMessage ->
                                dialog.dismiss()
                                Toast.makeText(requireContext(), "Error: $errorMessage", Toast.LENGTH_SHORT).show()

                            }
                        )

                    }else{
                        dialog.dismiss()
                        Toast.makeText(requireContext(), "Password is to short", Toast.LENGTH_SHORT).show()

                    }
                } else {
                    dialog.dismiss()
                    Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()

                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()

    }

}