package com.whereisthesolution.whereisihesolutionapp.domain.model.post.image

import com.whereisthesolution.whereisihesolutionapp.domain.model.post.Post
import kotlinx.serialization.Serializable

@Serializable
data class CreatePostRequest(
    val post: Post,
    val images: List<ImageUpload>
)