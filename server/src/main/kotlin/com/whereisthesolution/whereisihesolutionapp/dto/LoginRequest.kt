package com.whereisthesolution.whereisihesolutionapp.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)
