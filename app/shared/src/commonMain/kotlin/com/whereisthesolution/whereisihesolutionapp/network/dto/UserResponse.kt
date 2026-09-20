package com.whereisthesolution.whereisihesolutionapp.network.dto

import com.whereisthesolution.whereisihesolutionapp.domain.model.post.PrivacyLevel
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class UserResponse(
    val id: Long,
    val name: String,
    val email: String,
    val cpfHash: String? = null,
    val avatarUrl: String? = null,
    val privacyLevel: PrivacyLevel? = null,
    val reputationScore: Int? = null,
    val mainNeighborhood: String? = null,
    val city: String? = null,
    val createdAt: Instant,
)
