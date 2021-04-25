package com.whiteside.cafe.ui.account

import com.whiteside.cafe.api.firebase.FirebaseAuthManager
import com.whiteside.cafe.api.repository.UserRepository
import com.whiteside.cafe.model.User
import javax.inject.Inject

class UserPresenter @Inject constructor(
    val authRepo: FirebaseAuthManager,
    val userRepo: UserRepository
) {

    fun removeUser(userId: String, result: (Void) -> Unit) {
        userRepo.removeUser(userId)
            .addOnSuccessListener {
                result(it)
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }

    fun addUser(user: User, result: (Void) -> Unit) {
        userRepo.addNewUser(user)
            .addOnSuccessListener {
                result(it)
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }
}