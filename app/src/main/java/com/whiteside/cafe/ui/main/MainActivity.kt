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
import com.whiteside.cafe.common.BaseActivity
import com.whiteside.cafe.common.BaseListener
import com.whiteside.cafe.model.User
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    @Inject
    lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val animation = AnimationUtils.loadAnimation(this, R.anim.waiting_page_transition)
        val cafeName = findViewById<TextView?>(R.id.cafe_name)
        cafeName.startAnimation(animation)
        Handler(mainLooper).postDelayed({ checkInternet() }, 3000)
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
        if (auth.currentUser == null) {
            signUp()
        } else {
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUp() {
        mainPresenter.signInAnonymously(object : BaseListener<FirebaseUser>() {
            override fun onSuccess(result: FirebaseUser) {
                addUserToFirebase()

            }
        })
    }

    private fun addUserToFirebase() {
        val user = User()
        user.let {
            it.phoneNumber = "NA"
            it.uid = auth.uid!!
            it.cartItems = arrayListOf()
            it.wishList = arrayListOf()
            it.name = "unknown"
        }

        mainPresenter.addUserToFirebase(user, object : BaseListener<Unit>() {
            override fun onSuccess(result: Unit) {
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }

        })
    }
}