package com.incirkus.connect

import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.incirkus.connect.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeStatusBarColor("#13648D")

        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHost.navController)

        // Hier wird der Listener für das Erkennen der Tastatur-Änderungen hinzugefügt
        val rootView = findViewById<View>(android.R.id.content)
        rootView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val r = Rect()
                rootView.getWindowVisibleDisplayFrame(r)
                val screenHeight = rootView.rootView.height
                val keypadHeight = screenHeight - r.bottom

                // Überprüfen, ob wir uns im LoginFragment befinden
                val currentDestination = navHost.navController.currentDestination?.id
                val isInLoginFragment = currentDestination == R.id.loginFragment || currentDestination == R.id.passwordForgottenFragment

                if (!isInLoginFragment) {
                    if (keypadHeight > screenHeight * 0.15) {
                        // Tastatur ist sichtbar
                        binding.bottomNavigationView.visibility = View.GONE
                    } else {
                        // Tastatur ist nicht sichtbar
                        binding.bottomNavigationView.visibility = View.VISIBLE
                    }
                }
            }
        })

        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.chatsFragment -> {
                    binding.btnBack.isVisible = false
                    binding.mcvProfileImage.isVisible = false
                    binding.tvHeader2.visibility = View.VISIBLE
                    binding.tvHeader.visibility = View.INVISIBLE
                    binding.tvHeader2.text = getString(R.string.chats)
                    binding.toolbar.isGone = false

                }
                R.id.viewPageFragment -> {
                    binding.tvHeader2.visibility = View.GONE
                    binding.tvHeader.visibility = View.VISIBLE
                    binding.btnBack.isVisible = true
                    binding.mcvProfileImage.isVisible = true
                    viewModel.currentChatPartner2.observe(this, Observer { it ->
                        binding.ivProfilePicture.load(it.image)
                        binding.tvHeader.text = it.fullName
                    })


                    binding.toolbar.isGone = false

                }
                R.id.searchFragment -> {
                    binding.tvHeader2.visibility = View.GONE
                    binding.tvHeader.visibility = View.VISIBLE
                    binding.btnBack.isVisible = false
                    binding.mcvProfileImage.isVisible = false
                    binding.tvHeader.text = getString(R.string.search)
                    binding.toolbar.isGone = false

                }
                R.id.viewPageCalendarFragment -> {
                    val today = LocalDate.now()
                    val formatter = DateTimeFormatter.ofPattern("d. MMMM yyyy")
                    val formattedDate = today.format(formatter)
                    binding.tvHeader2.visibility = View.VISIBLE
                    binding.tvHeader.visibility = View.GONE
                    binding.btnBack.isVisible = false
                    binding.mcvProfileImage.isVisible = false
                    binding.tvHeader2.text = formattedDate
                    binding.toolbar.isGone = false

                }
                R.id.profileFragment -> {
                    binding.tvHeader2.visibility = View.GONE
                    binding.tvHeader.visibility = View.VISIBLE
                    binding.btnBack.isVisible = false
                    binding.mcvProfileImage.isVisible = false
                    binding.tvHeader.text = getString(R.string.profile)
                    binding.toolbar.isGone = false

                }
                R.id.loginFragment -> {
                    binding.tvHeader2.visibility = View.GONE
                    binding.tvHeader.visibility = View.VISIBLE
                    binding.toolbar.isGone = true
                    binding.bottomNavigationView.isGone = true
                }
                R.id.passwordForgottenFragment -> {
                    binding.tvHeader2.visibility = View.GONE
                    binding.tvHeader.visibility = View.VISIBLE
                    binding.toolbar.isGone = true
                    binding.bottomNavigationView.isGone = true
                }
                else -> {

                }

            }
        }

        binding.btnBack.setOnClickListener {

            val currentDestination = navHost.navController.currentDestination?.id
            val isInMessageFragment = currentDestination == R.id.viewPageFragment

            if (binding.btnBack.isVisible && isInMessageFragment){
                navHost.navController.navigateUp()
            }
        }

    }

    private fun changeStatusBarColor(color: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = android.graphics.Color.parseColor(color)
        }
    }

}