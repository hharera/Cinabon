package com.harera.login.mapper

import com.harera.login.LoginForm

object LoginRequestMapper {

    fun map(loginForm: LoginForm) : com.harera.repository.domain.LoginRequest {
        return com.harera.repository.domain.LoginRequest(
            username = loginForm.username,
            password = loginForm.password
        )
    }
}