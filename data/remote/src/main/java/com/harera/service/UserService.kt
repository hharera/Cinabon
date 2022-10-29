package com.harera.service

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.storage.UploadTask
import com.harera.service.domain.LoginCredentials
import com.harera.service.domain.ServiceDomainUser

interface UserService {

    fun addUser(serviceDomainUser: ServiceDomainUser): Task<Void>
    fun removeUser(userId: String): Task<Void>
    fun getUser(uid: String): Task<DocumentSnapshot>
    fun uploadUserImage(imageBitmap: Bitmap, uid: String): UploadTask

    fun signIn()
    fun loginAnonymously(): Task<AuthResult>
    fun signInByPhoneNumber()
    fun signOut()
    fun signInWithCredential(credential: PhoneAuthCredential): Task<AuthResult>
    fun getCurrentUser(): FirebaseUser?
    fun sendVerificationCode(
        phoneNumber: String,
        callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    )

    fun updatePassword(password: String): Task<Void>
    fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult>
    fun createCredential(verificationId: String, code: String): PhoneAuthCredential
    fun login(credential: AuthCredential): Task<AuthResult>
    suspend fun login(credential: LoginCredentials): AuthResult?
}