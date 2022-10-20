package com.harera.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.harera.common.utils.navigation.Arguments
import com.harera.confirm_login.ConfirmLoginActivity
import com.harera.login.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var bind: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = FragmentLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    private fun confirmLogin(phoneNumber: String) {
        val intent = Intent(this, ConfirmLoginActivity::class.java).apply {
            putExtra(Arguments.PHONE_NUMBER, phoneNumber)
        }
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
    }
}