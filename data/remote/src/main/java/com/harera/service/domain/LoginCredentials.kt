package com.harera.service.domain

import kotlinx.serialization.Serializable

@Serializable
data class LoginCredentials(
    var username: String,
    var password: String,
)
