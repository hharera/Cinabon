package com.harera.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harera.common.utils.Validity
import com.harera.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authManager: UserRepository,
) : ViewModel() {

    private var _phoneNumber = MutableLiveData<String>("")
    val username: LiveData<String> = _phoneNumber

    private var _phoneNumberValidity = MutableLiveData<Boolean>(false)
    val phoneNumberValidity: LiveData<Boolean> = _phoneNumberValidity

    private var _policyAccepted = MutableLiveData<Boolean>(false)
    val policyAccepted: LiveData<Boolean> = _policyAccepted

    private var _formState = MutableLiveData<LoginFormState>(false)
    val formState: LiveData<LoginFormState> = _formState

    fun checkValidity(ch: String) {
        username.value?.let {
            if (it.length < 11) {
                _phoneNumber.value = it.plus(ch)
            }
        }
    }

    private fun checkFormValidity() {
        val username = username.value
        val password = .value
        if (username.value.isNullOrBlank()) {
            _formState.value = LoginFormState(usernameError = R.string.error_empty_username)
        } else if (
            Validity.checkEmail(username).not() &&
            Validity.checkUsername(username).not() &&
            Validity.checkPhoneNumber(username).not()
        ) {
            _signupFormState.value = CustomerFormState(lastNameError = R.string.empty_name_error)
        } else if (username.value.isNullOrBlank()) {
            _signupFormState.value =
                CustomerFormState(phoneNumberError = R.string.empty_phone_error)
        } else if (_location.value == null) {
            _signupFormState.value = CustomerFormState(addressError = R.string.empty_location_error)
        } else {
            _signupFormState.value = CustomerFormState(isValid = true)
        }
    }

    fun changePhoneNumber(ch: String) {
        username.value?.let {
            if (it.length < 11) {
                _phoneNumber.value = it.plus(ch)
            }
        }
    }

    fun checkPhoneNumberValidity() {
        _phoneNumberValidity.value = Validity.checkPhoneNumber(_phoneNumber.value!!)
    }

    fun acceptPolicy() {
        _policyAccepted.value = !_policyAccepted.value!!
    }

    fun removeChar() {
        if (_phoneNumber.value!!.isNotEmpty()) {
            _phoneNumber.value = _phoneNumber.value!!.dropLast(1)
        }
    }

    fun isLoginAnonymously(): Boolean =
        runBlocking {
            authManager.getCurrentUser()!!.isAnonymous
        }

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
        checkPhoneNumberValidity()
    }
}