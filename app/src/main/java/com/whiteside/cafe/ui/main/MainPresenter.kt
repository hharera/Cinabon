package com.whiteside.cafe.ui.main

import com.google.firebase.auth.FirebaseUser
import com.whiteside.cafe.api.firebase.FirebaseAuthManager
import com.whiteside.cafe.api.repository.UserRepository
import com.whiteside.cafe.common.BaseListener
import com.whiteside.cafe.model.User
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val authManager: FirebaseAuthManager,
    private val userRepo: UserRepository
) {

    fun signInAnonymously(listener: BaseListener<FirebaseUser>) {
        listener.onLoading()
        authManager.signInAnonymously()
            .addOnSuccessListener {
                listener.onSuccess(it.user)
            }
            .addOnFailureListener {
                listener.onFailed(it)
            }
    }

    fun addNewUser(listener: BaseListener<User>) {
        listener.onLoading()
        val user = User()
        user.let {
            it.phoneNumber = "NA"
            it.uid = authManager.getCurrentUser()!!.uid
            it.cartItems = arrayListOf()
            it.wishList = arrayListOf()
            it.name = "unknown"
        }

        userRepo.addNewUser(user)
            .addOnCanceledListener {
                listener.onSuccess(user)
            }
            .addOnFailureListener {
                listener.onFailed(it)
            }
    }
}