package com.whiteside.cafe.common.repository

import com.google.android.gms.tasks.Task
import com.whiteside.cafe.model.User

interface UserRepository {

    fun addUser(user: User): Task<Void>
}