package com.harera.repository.impl

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.harera.hyperpanda.local.modelset.User
import com.harera.repository.abstraction.DBConstants
import com.harera.repository.abstraction.UserRepository
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class FirebaseUserRepository @Inject constructor(
    private val fStore: FirebaseFirestore,
    private val fStorage: FirebaseStorage,
) : UserRepository {

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
}
