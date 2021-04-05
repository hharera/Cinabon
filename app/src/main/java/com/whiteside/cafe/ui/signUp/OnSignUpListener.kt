package com.whiteside.cafe.ui.signUp

import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.whiteside.cafe.model.User

interface OnSignUpListener {
    open fun onVerificationCompleted(credential: PhoneAuthCredential?)
    open fun onVerificationFailed(e: FirebaseException?)
    open fun onCodeSent(
        verificationId: String,
        token: ForceResendingToken
    )

    open fun onGetUserDataSuccess(user: User)
    open fun onGetUserDataFailed(e: Exception)
    open fun onSignInFailed()
    open fun onSignInSuccess()
}