package com.whereisthesolution.whereisihesolutionapp.domain.model.post

import kotlinx.serialization.Serializable

@Serializable
data class PostAuthorResponse(
    val id: Long,
    val name: String
)