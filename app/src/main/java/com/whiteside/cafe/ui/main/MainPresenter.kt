package com.whiteside.cafe.ui.main

import com.google.firebase.auth.FirebaseUser
import com.whiteside.cafe.api.firebase.FirebaseAuthRepository
import com.whiteside.cafe.api.firebase.FirebaseUserRepo
import com.whiteside.cafe.common.BaseListener
import com.whiteside.cafe.model.User
import javax.inject.Inject

class MainPresenter @Inject constructor(
    val authRepo: FirebaseAuthRepository,
    val userRepo: FirebaseUserRepo
) {

    fun signInAnonymously(listener: BaseListener<FirebaseUser>) {
        authRepo.signInAnonymously()
            .addOnSuccessListener {
                listener.onSuccess(it.user)
            }
            .addOnFailureListener {
                listener.onFailed(it)
            }
    }

    fun addUserToFirebase(user: User, listener: BaseListener<Unit>) {
        userRepo.addUser(user)
    }
}