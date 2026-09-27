package com.whereisthesolution.whereisihesolutionapp.domain.model.post

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class PostResponse(
    val id: Long,
    val userId: Long,
    val title: String,
    val description: String,
    val category: ReportCategory,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val status: ReportStatus,
    val privacyLevel: PrivacyLevel,
    val imageUrls: List<String>,
    @Contextual
    val createdAt: Instant
)