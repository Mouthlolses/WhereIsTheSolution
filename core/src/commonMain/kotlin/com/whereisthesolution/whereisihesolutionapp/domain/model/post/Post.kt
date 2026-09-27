package com.whereisthesolution.whereisihesolutionapp.domain.model.post

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.Instant

@Serializable
data class Post(
    val id: Long,
    val userId: Long,
    val title: String,
    val description: String,
    val category: ReportCategory,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val status: ReportStatus = ReportStatus.PENDING,
    val privacyLevel: PrivacyLevel = PrivacyLevel.PUBLIC_TO_COMMUNITY,

    @Contextual
    val createdAt: Instant = Clock.System.now()
)

@Serializable
data class PostImage(
    val id: Long,
    val postId: Long,
    val imageUrl: String,
    val position: Int
)

@Serializable
enum class PrivacyLevel {
    PUBLIC_TO_COMMUNITY,
    ANONYMOUS_TO_COMMUNITY,
    FULLY_ANONYMOUS
}

@Serializable
enum class ReportCategory {
    BURACO_NA_VIA,
    ILUMINACAO_PUBLICA,
    LIXO_IRREGULAR,
    VAZAMENTO_AGUA,
    OUTROS
}

@Serializable
enum class ReportStatus {
    PENDING,
    SENT_TO_CITY_HALL,
    IN_ANALYSIS,
    RESOLVED
}