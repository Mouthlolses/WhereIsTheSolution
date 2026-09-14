package com.whereisthesolution.whereisihesolutionapp.domain.model.post

import java.time.Instant

data class Post(
    val id: Long,
    val userId: String, // ID do autor (chave estrangeira)
    val title: String,
    val description: String,
    val imageUrls: List<String>,
    val category: ReportCategory, // Ex: BURACO_NA_VIA, ILUMINACAO, LIXO_IRREGULAR
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val status: ReportStatus = ReportStatus.PENDING, // PENDING, SENT_TO_CITY_HALL, RESOLVED
    val createdAt: Instant = Instant.now()
)


enum class PrivacyLevel {
    PUBLIC_TO_COMMUNITY,
    ANONYMOUS_TO_COMMUNITY,
    FULLY_ANONYMOUS
}

enum class ReportCategory {
    BURACO_NA_VIA,
    ILUMINACAO_PUBLICA,
    LIXO_IRREGULAR,
    VAZAMENTO_AGUA,
    OUTROS
}

enum class ReportStatus {
    PENDING,
    SENT_TO_CITY_HALL,
    IN_ANALYSIS,
    RESOLVED
}