package com.harera.hyperpanda

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.harera.common.base.BaseActivity
import com.harera.common.internet.NoInternetActivity
import com.harera.navigation.userlogin.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Log.d(TAG, "onCreate: ${Build.VERSION.SDK_INT}")
            splashScreen.apply {
                setKeepOnScreenCondition {
                    true
                }
                setOnExitAnimationListener { splashScreenViewProvider ->
                    splashScreenViewProvider.view
                        .animate()
                        .alpha(0f)
                        .setDuration(1000)
                        .withEndAction {
                            splashScreenViewProvider.remove()
                        }
                }
            }
        } else {
            setContentView(R.layout.activity_main)
            setupAnimation()
        }

        mainViewModel.checkLogin()
        waitDelay()
    }

    private fun waitDelay() {
        mainViewModel.startDelay()

        mainViewModel.delayEnded.observe(this) { delayFinished ->
            if (delayFinished) {
                mainViewModel.isLoggedIn.observe(this) { isLoggedIn ->
                    if (isLoggedIn) {
                        finishActivity()
                    }
                }
            }
        }
    }

    private fun finishActivity() {
        startActivity(
            Intent(
                this@MainActivity,
                LoginActivity::class.java
            )
        ).also {
            finish()
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