package com.whereisthesolution.whereisihesolutionapp.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserCredential(
    val userId: Long,
    val passwordHash: String
)