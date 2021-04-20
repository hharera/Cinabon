package com.whiteside.cafe.api.repository

import com.google.android.gms.tasks.Task
import com.whiteside.cafe.model.User

interface UserRepository {

    fun addUser(user: User): Task<Void>
}