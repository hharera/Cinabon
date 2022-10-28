package com.harera.splash

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.harera.common.base.BaseActivity
import com.harera.common.internet.NoInternetActivity
import com.harera.splash.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private val splashViewModel: SplashViewModel by viewModels()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashViewModel.startDelay()
        splashViewModel.checkLogin()
    }

    override fun onResume() {
        super.onResume()
        waitDelay()
    }

    private fun waitDelay() {
        splashViewModel.delayEnded.observe(this) { delayFinished ->
            if (delayFinished) {
                checkLoginState()
            }
        }
    }

    private fun checkLoginState() {
        splashViewModel.isLoggedIn.observe(this) { isLoggedIn ->
            if (isLoggedIn) {
                startActivity("com.harera.home.HomeActivity")
            } else {
                startActivity("com.harera.manager.login.LoginActivity")
            }
        }
    }

    private fun setupAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.waiting_page_transition)
        val appName = findViewById<TextView?>(R.id.app_name)
        val apple = findViewById<ImageView?>(R.id.apple)
        apple.startAnimation(animation)
        appName.startAnimation(animation)
    }

    private fun showNoInternet() {
        val intent = Intent(this@SplashActivity, NoInternetActivity::class.java)
        startActivity(intent)
        finish()
    }
}


