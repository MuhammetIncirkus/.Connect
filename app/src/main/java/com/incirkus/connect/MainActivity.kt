package com.incirkus.connect

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import coil.transform.CircleCropTransformation
import com.google.firebase.appcheck.ktx.appCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.Firebase
import com.google.firebase.appcheck.appCheck
import com.google.firebase.initialize
import com.incirkus.connect.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModel

    private fun init() {
        // [START appcheck_initialize]
        Firebase.initialize(context = this)
        Firebase.appCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance(),
        )
        // [END appcheck_initialize]
    }

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
                    binding.toolbar.isGone = false
                    binding.bottomNavigationView.isGone = false

                }
                R.id.messagesFragment -> {
                    binding.btnBack.isVisible = true
                    binding.mcvProfileImage.isVisible = true
                    binding.tvHeader.text = getString(R.string.messages)
                    binding.toolbar.isGone = false
                    binding.bottomNavigationView.isGone = false
                }
                R.id.searchFragment -> {
                    binding.btnBack.isVisible = false
                    binding.mcvProfileImage.isVisible = false
                    binding.tvHeader.text = getString(R.string.search)
                    binding.toolbar.isGone = false
                    binding.bottomNavigationView.isGone = false

                }
                R.id.calendarFragment -> {
                    binding.btnBack.isVisible = false
                    binding.mcvProfileImage.isVisible = false
                    binding.tvHeader.text = getString(R.string.calendar)
                    binding.toolbar.isGone = false
                    binding.bottomNavigationView.isGone = false
                }
                R.id.profileFragment -> {
                    binding.btnBack.isVisible = false
                    binding.mcvProfileImage.isVisible = false
                    binding.tvHeader.text = getString(R.string.profile)
                    binding.toolbar.isGone = false
                    binding.bottomNavigationView.isGone = false
                }
                R.id.loginFragment -> {
                    binding.btnBack.isVisible = false
                    binding.mcvProfileImage.isVisible = false
                    binding.tvHeader.text = getString(R.string.profile)
                    binding.toolbar.isGone = true
                    binding.bottomNavigationView.isGone = true
                }
                else -> {
                    binding.toolbar.isGone = false
                    binding.bottomNavigationView.visibility = View.GONE
                }

            }
        }

        // Hier wird der Listener für das Erkennen der Tastatur-Änderungen hinzugefügt
        val rootView = findViewById<View>(android.R.id.content)
        rootView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val r = Rect()
                rootView.getWindowVisibleDisplayFrame(r)
                val screenHeight = rootView.rootView.height
                val keypadHeight = screenHeight - r.bottom

                if (keypadHeight > screenHeight * 0.15) {
                    // Tastatur ist sichtbar
                    binding.bottomNavigationView.visibility = View.GONE
                } else {
                    // Tastatur ist nicht sichtbar
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
            }
        })

    }


}