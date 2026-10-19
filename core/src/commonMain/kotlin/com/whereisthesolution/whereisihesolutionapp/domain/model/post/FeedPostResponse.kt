package com.whereisthesolution.whereisihesolutionapp.domain.model.post

import kotlinx.serialization.Serializable

@Serializable
data class FeedPostResponse(
    val post: PostResponse,
    val author: PostAuthorResponse?
)