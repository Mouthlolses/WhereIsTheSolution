package com.whereisthesolution.whereisihesolutionapp.domain.model.user

import com.whereisthesolution.whereisihesolutionapp.domain.model.post.Post
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.PrivacyLevel
import java.time.Instant

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val password: String,
    val cpfHash: String? = null,
    val avatarUrl: String? = null,
    val privacyLevel: PrivacyLevel? = PrivacyLevel.PUBLIC_TO_COMMUNITY,
    val reputationScore: Int? = 100,
    val isVerified: Boolean = false,
    val mainNeighborhood: String? = null,
    val city: String? = null,

    // Lista de posts criados por este usuário
    val posts: List<Post> = emptyList(),

    val createdAt: Instant = Instant.now(),
    val isActive: Boolean = true
)
