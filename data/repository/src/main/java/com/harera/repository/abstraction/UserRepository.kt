package com.harera.repository.abstraction

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.storage.UploadTask
import com.harera.hyperpanda.local.modelset.User

interface UserRepository {

    fun addUser(user: User): Task<Void>
    fun removeUser(userId: String): Task<Void>
    fun getUser(uid: String): Task<DocumentSnapshot>
    fun uploadUserImage(imageBitmap: Bitmap, uid: String): UploadTask
}