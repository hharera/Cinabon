package com.whiteside.cafe.api.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.whiteside.cafe.model.User

interface UserRepository {

    fun addUser(user: User): Task<Void>
    fun removeUser(userId: String): Task<Void>
    fun getUser(userId: String): Task<DocumentSnapshot>
}