package com.harera.navigation.home

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.englizya.feature.ticket.navigation.home.R
import com.englizya.feature.ticket.navigation.home.databinding.ActivityHomeBinding
import com.harera.common.base.BaseActivity

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    companion object {
        private const val TAG = "HomeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        navController = Navigation.findNavController(this, R.id.nav_host)
//
//        getExtras()
//        setupBottomNavigation()
    }
//
//    private fun setupBottomNavigation() {
//        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
//        binding.bottomNavigation.setupWithNavController(navController)
//    }
//
//    override fun onStart() {
//        super.onStart()
//        setupListeners()
//    }
//
//    private fun setupListeners() {
//        binding.bottomNavigation.setOnItemSelectedListener {
//            true
//        }
//    }
//
//    private fun getExtras() {
//        intent?.extras?.getString(Arguments.DESTINATION)?.let {
//
//        }
//    }
}