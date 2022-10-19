package com.harera.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authManager: com.harera.repository.abstraction.repository.AuthManager,
    private val userRepo: com.harera.repository.abstraction.repository.UserRepository
) : ViewModel() {

    val delayEnded: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoggedIn: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkLogin() {
        isLoggedIn.value = authManager.getCurrentUser() != null
    }

    fun startDelay() {
        viewModelScope.launch {
            delay(1500)
            delayEnded.value = true
        }
    }
}