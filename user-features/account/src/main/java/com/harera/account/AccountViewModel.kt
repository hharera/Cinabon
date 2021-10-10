package com.harera.account

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.harera.common.base.BaseViewModel
import com.harera.repository.repository.AuthManager
import com.harera.repository.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val userRepository: UserRepository,
) : BaseViewModel() {

    private var _userIsAnonymous = MutableLiveData<Boolean>()
    val userIsAnonymous: LiveData<Boolean> = _userIsAnonymous

    private var _userImage = MutableLiveData<Bitmap>()
    val userImage: LiveData<Bitmap> = _userImage

    fun getUser() {

    }

    fun checkUser() {
        _userIsAnonymous.value = authManager.getCurrentUser()!!.isAnonymous
    }

    fun setProfileImage(imageBitmap: Bitmap) {
        _userImage.value = imageBitmap
    }
}