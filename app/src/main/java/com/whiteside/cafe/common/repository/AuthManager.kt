package com.whiteside.cafe.common.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthManager {

    fun signIn()
    fun signInAnonymously(): Task<AuthResult>
    fun signInPhoneNumber()
    fun signOut()
}