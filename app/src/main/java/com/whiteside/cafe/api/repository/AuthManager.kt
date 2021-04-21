package com.whiteside.cafe.api.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

interface AuthManager {

    fun signIn()
    fun signInAnonymously(): Task<AuthResult>
    fun signInByPhoneNumber()
    fun signOut()
    fun signInWithCredential(credential: PhoneAuthCredential): Task<AuthResult>
    fun getCurrentUser(): FirebaseUser
    fun sendVerificationCode(
        phoneNumber: String,
        createCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    )
}