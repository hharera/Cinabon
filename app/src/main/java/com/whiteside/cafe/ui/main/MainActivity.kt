package com.whiteside.cafe.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.google.firebase.auth.FirebaseUser
import com.whiteside.cafe.HomeActivity
import com.whiteside.cafe.NoInternetActivity
import com.whiteside.cafe.R
import com.whiteside.cafe.api.repository.AuthManager
import com.whiteside.cafe.common.BaseActivity
import com.whiteside.cafe.common.BaseListener
import com.whiteside.cafe.databinding.ActivityMainBinding
import com.whiteside.cafe.model.User
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    @Inject
    lateinit var mainPresenter: MainPresenter

    @Inject
    lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        setupAnimation()

        Handler(mainLooper).postDelayed({ checkInternet() }, 3000)
    }

    private fun setupAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.waiting_page_transition)
        val cafeName = findViewById<TextView?>(R.id.cafe_name)
        cafeName.startAnimation(animation)
    }

    private fun checkInternet() {
        if (connection.activeNetwork != null) {
            checkSignUp()
        } else {
            showNoInternet()
        }
    }

    private fun showNoInternet() {
        val intent = Intent(this@MainActivity, NoInternetActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkSignUp() {
        if (authManager.getCurrentUser() == null) {
            signUp()
        } else {
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUp() {
        mainPresenter.signInAnonymously(object : BaseListener<FirebaseUser> {
            override fun onSuccess(result: FirebaseUser) {
                handleSuccess()
                addNewUser()
            }

            override fun onFailed(exception: Exception) {
                handleFailure(exception)
            }

            override fun onLoading() {
                handleLoading()
            }
        })
    }

    private fun addNewUser() {
        mainPresenter.addNewUser(object : BaseListener<User> {
            override fun onSuccess(result: User) {
                handleSuccess()
                goToHomeActivity()
            }

            override fun onFailed(exception: Exception) {
                handleFailure(exception)
            }

            override fun onLoading() {
                handleLoading()
            }
        })
    }

    private fun goToHomeActivity() {
        val intent = Intent(this@MainActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}