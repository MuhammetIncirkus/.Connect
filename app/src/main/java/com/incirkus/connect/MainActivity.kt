package com.incirkus.connect

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import coil.transform.CircleCropTransformation
import com.incirkus.connect.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHost.navController)

        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.chatsFragment -> {
                    binding.btnBack.isVisible = false
                    binding.mcvProfileImage.isVisible = false
                    binding.tvHeader.text = getString(R.string.chats)

                }
                R.id.messagesFragment -> {
                    binding.btnBack.isVisible = true
                    binding.mcvProfileImage.isVisible = true
                    binding.tvHeader.text = getString(R.string.messages)
                }
                R.id.searchFragment -> {
                    binding.btnBack.isVisible = false
                    binding.mcvProfileImage.isVisible = false
                    binding.tvHeader.text = getString(R.string.search)

                }
                R.id.calendarFragment -> {
                    binding.btnBack.isVisible = false
                    binding.mcvProfileImage.isVisible = false
                    binding.tvHeader.text = getString(R.string.calendar)
                }
                R.id.profileFragment -> {
                    binding.btnBack.isVisible = false
                    binding.mcvProfileImage.isVisible = false
                    binding.tvHeader.text = getString(R.string.profile)
                }
                else -> {

                }

            }
        }


    }


}