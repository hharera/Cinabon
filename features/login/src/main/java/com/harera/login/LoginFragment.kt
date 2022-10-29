package com.harera.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.harera.common.afterTextChanged
import com.harera.common.base.BaseFragment
import com.harera.common.extension.toException
import com.harera.common.utils.Package
import com.harera.login.databinding.FragmentLoginBinding
import com.harera.repository.uitls.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        binding.username.afterTextChanged {
            loginViewModel.setEmail(it)
        }

        binding.password.afterTextChanged {
            loginViewModel.setPassword(it)
        }

        binding.login.setOnClickListener {
            checkFormValidity()
        }
    }

    private fun checkFormValidity() {
        loginViewModel.loginFormState.value?.run {
            if (isValid == true) {
                login()
            } else {
                usernameError?.let {
                    binding.usernameTL.error = getString(it)
                }
                passwordError?.let {
                    binding.passwordTL.error = getString(it)
                }
            }
        }
    }

    private fun login() {
        loginViewModel.login().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    checkUserCredentials(it.data)
                }

                is Resource.Error -> {
                    handleError(it.error?.toException())
                }

                is Resource.Loading -> {
                    handleLoading()
                }
            }
        }
    }

    private fun checkUserCredentials(data: Boolean?) {
        if (data == true) {
            navigateToHome()
        } else {
//            TODO: show error dialog
            Toast.makeText(
                context,
                "Invalid credentials",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun navigateToHome() {
        startActivity("${Package.MANAGER_NAVIGATION}.HomeActivity").also {
            activity?.finish()
        }
    }
}