package com.englizya.navigation.login

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.englizya.common.base.BaseActivity
import com.englizya.common.utils.navigation.Arguments
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.feature.ticket.navigation.login.R
import com.englizya.feature.ticket.navigation.login.databinding.ActivityLoginBinding


class LoginActivity : BaseActivity() {

    private lateinit var bind: ActivityLoginBinding
    private lateinit var navController: NavController
    private val TAG = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)

        navController = Navigation.findNavController(this, R.id.navView)

        getExtras()
    }

    private fun getExtras() {
        intent?.extras?.getString(Arguments.DESTINATION)?.let {
            navController.navigate(NavigationUtils.getUriNavigation(Domain.ENGLIZYA_PAY, it, Destination.TICKET))
        }
    }
}