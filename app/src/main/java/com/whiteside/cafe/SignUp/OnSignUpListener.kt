package com.whiteside.cafe.SignUp

import Model.FirebaseUser
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken

interface OnSignUpListener {
    open fun onVerificationCompleted(credential: PhoneAuthCredential?)
    open fun onVerificationFailed(e: FirebaseException?)
    open fun onCodeSent(
        verificationId: String,
        token: ForceResendingToken
    )

    open fun onGetUserDataSuccess(firebaseUser: FirebaseUser?)
    open fun onGetUserDataFailed(e: Exception?)
    open fun onSignInFailed()
    open fun onSignInSuccess()
}