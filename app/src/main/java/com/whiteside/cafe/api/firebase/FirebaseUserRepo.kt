package com.whiteside.cafe.api.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.api.repository.UserRepository
import com.whiteside.cafe.model.User
import javax.inject.Inject

class FirebaseUserRepo @Inject constructor() : UserRepository {
    val fStore by lazy { FirebaseFirestore.getInstance() }

    override fun addNewUser(user: User) =
        fStore.collection("Users")
            .document(user.uid)
            .set(user)

    override fun removeUser(userId: String): Task<Void> =
        fStore.collection("Users")
            .document(userId)
            .delete()

    override fun getUser(userId: String): Task<DocumentSnapshot> =
        fStore.collection("Users")
            .document(userId)
            .get()
}
