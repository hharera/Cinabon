package com.harera.managehyper

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Tasks
import com.harera.common.base.BaseActivity
import com.harera.common.internet.NoInternetActivity
import com.harera.repository.repository.AuthManager
import com.harera.repository.repository.UserRepository
import com.harera.common.utils.Response
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

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
        GlobalScope.launch(Dispatchers.Main) {
            delay(1500)
            startActivity(
                Intent(this@MainActivity, HomeActivity::class.java)
            )
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


@HiltViewModel
class MainViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val userRepo: UserRepository
) : ViewModel() {

    fun signInAnonymously() = liveData {
        viewModelScope.launch(Dispatchers.IO) {
            emit(Response.loading(null))

            val task = authManager.loginAnonymously()
            Tasks.await(task)
            emit(
                if (task.isSuccessful)
                    Response.success(data = task.result)
                else
                    Response.error(error = task.exception, data = null)
            )
        }
    }

    fun checkLogin() {
        if (authManager.getCurrentUser() == null)
            authManager.loginAnonymously()
    }
}