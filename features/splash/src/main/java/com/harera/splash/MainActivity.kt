package com.harera.splash

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import com.harera.common.base.BaseActivity
import com.harera.common.internet.NoInternetActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.checkLogin()
        setupAnimation()
        waitDelay()
    }

    private fun waitDelay() {
        mainViewModel.startDelay()

        mainViewModel.delayEnded.observe(this) { delayFinished ->
            if (delayFinished) {
                mainViewModel.isLoggedIn.observe(this) { isLoggedIn ->
                    if (isLoggedIn) {
                        startActivity("com.harera.home.HomeActivity")
                    } else {
                        startActivity("com.harera.login.LoginActivity")
                    }
                }
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
        val intent = Intent(this@MainActivity, NoInternetActivity::class.java)
        startActivity(intent)
        finish()
    }
}


