package com.whereisthesolution.whereisihesolutionapp.domain.model.post

import kotlinx.serialization.Serializable

@Serializable
data class CreatePostRequest(
    val title: String,
    val description: String,
    val category: ReportCategory,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val privacyLevel: PrivacyLevel,
    val imageUrls: List<String>
)