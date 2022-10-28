package com.harera.repository.impl

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.harera.repository.DBConstants
import com.harera.repository.UserRepository
import com.harera.repository.domain.LoginRequest
import com.harera.repository.domain.User
import com.harera.repository.mapper.OfferMapper
import com.harera.repository.uitls.Resource
import com.harera.repository.uitls.networkBoundResource
import com.harera.service.UserService
import com.harera.service.domain.LoginCredentials
import com.harera.service.domain.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val fStore: FirebaseFirestore,
    private val fStorage: FirebaseStorage,
    private val userService: UserService,
) : UserRepository {

    override fun signIn() {
    }

    override fun loginAnonymously(): Task<AuthResult> = auth.signInAnonymously()

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
            .setCallbacks(callback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun updatePassword(password: String) =
        auth.currentUser!!.updatePassword(password)

    override fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult> =
        auth.signInWithEmailAndPassword(email, password)

    override fun createCredential(verificationId: String, code: String) =
        PhoneAuthProvider.getCredential(verificationId, code)

    override fun login(credential: AuthCredential) =
        auth.signInWithCredential(credential)


    override fun addUser(user: User) =
        fStore.collection("Users")
            .document(user.uid)
            .set(user)

    override fun removeUser(userId: String): Task<Void> =
        fStore.collection("Users")
            .document(userId)
            .delete()

    override fun getUser(uid: String): Task<DocumentSnapshot> =
        fStore.collection("Users")
            .document(uid)
            .get()

    override fun uploadUserImage(imageBitmap: Bitmap, uid: String): UploadTask {
        val inputStream = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, inputStream)

        return fStorage
            .reference
            .child(DBConstants.USERS)
            .child(uid)
            .putBytes(inputStream.toByteArray())
    }

    override fun login(request: LoginRequest): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        userService.login(
            LoginCredentials(
                username = request.username,
                password = request.password
            )
        ).run {
            if (this != null) {
                emit(Resource.Success(user != null))
            } else {
                emit(Resource.Error(IOException("Error logging in")))
            }
        }
    }
}