package com.harera.service.domain

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(val jwt: String)