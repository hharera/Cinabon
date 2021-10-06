package com.harera.hyperpanda

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.harera.hyperpanda.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var bind: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bind.root)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bind.navView.setupWithNavController(navController = navController)

        setUpWithBottomNav()
        setUpListeners()
    }

    private fun setUpWithBottomNav() {
        bind.bottomNav.setupWithNavController(navController)
    }

    private fun setUpListeners() {
        bind.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.favorite -> {
                    navController.navigate(R.id.navigation_wishlist)
                }

                R.id.cart -> {
                    navController.navigate(R.id.navigation_cart)
                }

                R.id.search -> {
                    navController.navigate(R.id.navigation_search)
                }

                R.id.profile -> {
                    bind.navView.showContextMenu()
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    override fun onBackPressed() {
        navController.popBackStack()
    }
}