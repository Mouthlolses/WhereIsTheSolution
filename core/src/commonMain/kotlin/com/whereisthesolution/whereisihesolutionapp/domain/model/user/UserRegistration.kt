package com.whereisthesolution.whereisihesolutionapp.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserRegistration(
    val user: User,
    val passwordHash: String
)