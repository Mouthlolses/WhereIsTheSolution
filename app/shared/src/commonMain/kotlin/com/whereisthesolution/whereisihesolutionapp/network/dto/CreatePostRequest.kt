package com.whereisthesolution.whereisihesolutionapp.network.dto

import com.whereisthesolution.whereisihesolutionapp.domain.model.post.image.ImageUpload
import kotlinx.serialization.Serializable

@Serializable
data class CreatePostRequest(
    val title: String,
    val description: String,
    val location: String,
    val images: List<ImageUpload>?
)
