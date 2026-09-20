package com.whereisthesolution.whereisihesolutionapp.data.mappers

import com.whereisthesolution.whereisihesolutionapp.domain.model.user.User
import com.whereisthesolution.whereisihesolutionapp.network.dto.UserResponse

//Converter UserResponse → User
fun UserResponse.toDomain(): User {
    return User(
        id = id,
        name = name,
        email = email,
        cpfHash = cpfHash,
        avatarUrl = avatarUrl,
        privacyLevel = privacyLevel,
        reputationScore = reputationScore,
        mainNeighborhood = mainNeighborhood,
        city = city,
        createdAt = createdAt,
    )
}