package com.whereisthesolution.whereisihesolutionapp.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequest(
    val name: String,
    val email: String,
    val password: String
)
