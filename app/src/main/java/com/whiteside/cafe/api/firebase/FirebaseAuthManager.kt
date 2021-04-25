package com.whiteside.cafe.api.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.whiteside.cafe.api.repository.AuthManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FirebaseAuthManager @Inject constructor() : AuthManager {
    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun signIn() {
    }

    override fun signInAnonymously(): Task<AuthResult> = auth.signInAnonymously()

    override fun signInByPhoneNumber() {
    }

    override fun signOut() = auth.signOut()

    override fun signInWithCredential(credential: PhoneAuthCredential) =
        auth.signInWithCredential(credential)

    override fun getCurrentUser(): FirebaseUser? = auth.currentUser

    override fun sendVerificationCode(
        phoneNumber: String,
        callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
//            .setActivity(activity)
            .setCallbacks(callback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun updatePassword(password : String) =
        auth.currentUser!!.updatePassword(password)
}