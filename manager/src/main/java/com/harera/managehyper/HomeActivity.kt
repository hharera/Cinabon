package com.harera.managehyper

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.harera.common.base.BaseActivity
import com.harera.managehyper.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity() {
    private lateinit var navController: NavController
    private lateinit var bind: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bind.root)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }
}