package com.harera.repository

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.storage.UploadTask
import com.harera.repository.domain.LoginRequest
import com.harera.repository.domain.User
import com.harera.repository.uitls.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

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
    fun login(request: LoginRequest): Flow<Resource<Boolean>>
    fun addUser(user: User): Task<Void>
    fun removeUser(userId: String): Task<Void>
    fun getUser(uid: String): Task<DocumentSnapshot>
    fun uploadUserImage(imageBitmap: Bitmap, uid: String): UploadTask
}
