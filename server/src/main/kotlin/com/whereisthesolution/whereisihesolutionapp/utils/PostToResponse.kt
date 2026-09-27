package com.whereisthesolution.whereisihesolutionapp.utils

import com.whereisthesolution.whereisihesolutionapp.domain.model.post.Post
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.PostResponse

fun Post.toResponse(
    imageUrls: List<String>
): PostResponse {
    return PostResponse(
        id = id,
        userId = userId,
        title = title,
        description = description,
        category = category,
        latitude = latitude,
        longitude = longitude,
        address = address,
        status = status,
        privacyLevel = privacyLevel,
        imageUrls = imageUrls,
        createdAt = createdAt,
    )
}