package com.whereisthesolution.whereisihesolutionapp.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val user: UserResponse
)