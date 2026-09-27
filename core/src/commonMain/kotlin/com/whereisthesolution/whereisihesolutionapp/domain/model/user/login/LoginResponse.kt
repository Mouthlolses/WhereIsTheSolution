package com.whereisthesolution.whereisihesolutionapp.domain.model.user.login

import com.whereisthesolution.whereisihesolutionapp.domain.model.user.User
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val user: User
)