package com.harera.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.harera.common.utils.Validity
import com.harera.login.mapper.LoginRequestMapper
import com.harera.repository.UserRepository
import com.harera.repository.uitls.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private var _email = MutableLiveData<String>("")
    val email: LiveData<String> = _email

    private var _password = MutableLiveData<String>("")
    val password: LiveData<String> = _password

    private var _loginFormState = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginFormState

    private fun checkFormValidity() {
        val username = email.value
        val password = _password.value

        if (username.isNullOrBlank()) {
            _loginFormState.value = LoginFormState(usernameError = R.string.error_empty_username)
        } else if (Validity.checkEmail(username).not()) {
            _loginFormState.value = LoginFormState(usernameError = R.string.error_invalid_username)
        } else if (password.isNullOrBlank()) {
            _loginFormState.value = LoginFormState(passwordError = R.string.error_empty_password)
        } else if (Validity.checkPassword(password).not()) {
            _loginFormState.value = LoginFormState(passwordError = R.string.error_invalid_password)
        } else {
            _loginFormState.value = LoginFormState(isValid = true)
        }
    }

    fun setEmail(value: String) {
        _email.value = value
        checkFormValidity()
    }

    fun setPassword(value: String) {
        _password.value = value
        checkFormValidity()
    }

    fun login(): LiveData<Resource<Boolean>> {
        val loginForm = LoginForm(
            username = email.value!!,
            password = password.value!!
        )
        val loginRequest = LoginRequestMapper.map(loginForm)
        return userRepository.login(loginRequest).asLiveData()
    }
}