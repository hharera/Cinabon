package com.whiteside.cafe.api.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.whiteside.cafe.api.repository.UserRepository
import com.whiteside.cafe.model.User
import javax.inject.Inject

class FirebaseUserRepo @Inject constructor() : UserRepository {
    val fStore by lazy { FirebaseFirestore.getInstance() }

    override fun addUser(user: User) =
        fStore.collection("Users")
            .document(user.uid)
            .set(user)
}
