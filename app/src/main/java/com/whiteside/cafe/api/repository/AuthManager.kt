package com.whiteside.cafe.api.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthManager {

    fun signIn()
    fun signInAnonymously(): Task<AuthResult>
    fun signInByPhoneNumber()
    fun signOut()
}