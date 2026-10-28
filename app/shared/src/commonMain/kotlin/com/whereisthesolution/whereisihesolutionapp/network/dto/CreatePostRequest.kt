package com.whereisthesolution.whereisihesolutionapp.network.dto

import com.whereisthesolution.whereisihesolutionapp.domain.model.post.image.ImageUpload
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class CreatePostRequest(
    val title: String,
    val description: String,
    @Transient
    val images: List<ImageUpload>? = null
)
