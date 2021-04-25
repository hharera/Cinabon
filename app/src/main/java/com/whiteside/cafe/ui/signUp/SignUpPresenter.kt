package com.whiteside.cafe.ui.signUp

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.whiteside.cafe.api.repository.AuthManager
import com.whiteside.cafe.api.repository.UserRepository
import com.whiteside.cafe.model.User
import javax.inject.Inject

class SignUpPresenter @Inject constructor(
    val authManager: AuthManager,
    val userRepository: UserRepository
) {


    fun sendVerificationCode(phoneNumber: String, result: (String) -> Unit) {
        authManager.sendVerificationCode(phoneNumber, createCallBack(result))
    }

    private fun createCallBack(result: (String) -> Unit) =
        object : OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            }

            override fun onVerificationFailed(e: FirebaseException) {
                result("wrong")
                e.printStackTrace()
            }

            override fun onCodeSent(
                verificationId: String,
                token: ForceResendingToken
            ) {
                result(verificationId)
            }
        }

    fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        result: (FirebaseUser?) -> Unit
    ) {
        result(authManager.signInWithCredential(credential).result.user)
    }

    fun removeUser(userID: String) {
        userRepository.removeUser(userID)
    }

    fun addUser(user: User) {
        userRepository.addNewUser(user)
            .addOnSuccessListener {

            }
    }

    fun getUser(userID: String, result: (User) -> Unit) {
        userRepository.getUser(userID)
            .addOnSuccessListener {
                result(it.toObject(User::class.java)!!)
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }

    fun createCredential(code: String, verificationId: String) =
        PhoneAuthProvider.getCredential(verificationId, code)
}