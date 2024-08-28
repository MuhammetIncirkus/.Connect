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
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import coil.load
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
                val isInLoginFragment = currentDestination == R.id.loginFragment

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
                    binding.tvHeader.text = getString(R.string.chats)
                    binding.toolbar.isGone = false


                }
                R.id.viewPageFragment -> {
                    binding.btnBack.isVisible = true
                    binding.mcvProfileImage.isVisible = true
                    viewModel.currentChatPartner2.observe(this, Observer { it ->
                        binding.ivProfilePicture.load(it.image)
                        binding.tvHeader.text = it.fullName
                    })


                    binding.toolbar.isGone = false

                }
                R.id.searchFragment -> {
                    binding.btnBack.isVisible = false
                    binding.mcvProfileImage.isVisible = false
                    binding.tvHeader.text = getString(R.string.search)
                    binding.toolbar.isGone = false


                }
                R.id.calendarFragment -> {
                    binding.btnBack.isVisible = false
                    binding.mcvProfileImage.isVisible = false
                    binding.tvHeader.text = getString(R.string.calendar)
                    binding.toolbar.isGone = false

                }
                R.id.profileFragment -> {
                    binding.btnBack.isVisible = false
                    binding.mcvProfileImage.isVisible = false
                    binding.tvHeader.text = getString(R.string.profile)
                    binding.toolbar.isGone = false

                }
                R.id.loginFragment -> {
                    binding.toolbar.isGone = true
                    binding.bottomNavigationView.isGone = true
                }
                else -> {

                }

            }
        }

        binding.btnBack.setOnClickListener {

            val currentDestination = navHost.navController.currentDestination?.id
            val isInMessageFragment = currentDestination == R.id.messagesFragment

            if (binding.btnBack.isVisible && isInMessageFragment){
                navHost.navController.navigate(R.id.chatsFragment)
//                viewModel.clearMessagelist()
            }
        }

    }


}