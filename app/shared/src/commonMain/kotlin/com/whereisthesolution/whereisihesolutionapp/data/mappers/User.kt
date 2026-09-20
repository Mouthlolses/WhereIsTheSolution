package com.whereisthesolution.whereisihesolutionapp.data.mappers

import com.whereisthesolution.whereisihesolutionapp.data.entity.UserEntity
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.PrivacyLevel
import com.whereisthesolution.whereisihesolutionapp.domain.model.user.User
import kotlin.time.Instant

// Converter Entity do Room para o Model do Core
fun UserEntity.toDomain(): User {
    return User(
        id = id,
        name = name,
        email = email,
        cpfHash = cpfHash,
        avatarUrl = avatarUrl,
        privacyLevel = privacyLevel?.let {
            PrivacyLevel.valueOf(it)
        },
        reputationScore = reputationScore,
        isVerified = isVerified,
        mainNeighborhood = mainNeighborhood,
        city = city,
        createdAt = Instant.fromEpochMilliseconds(createdAtTimestamp),
        isActive = isActive
    )
}

// Converter Entity do Room para o Model do Core
fun User.toEntity() = UserEntity(
    id = id,
    name = name,
    email = email,
    cpfHash = cpfHash,
    avatarUrl = avatarUrl,
    privacyLevel = privacyLevel?.name,
    reputationScore = reputationScore,
    isVerified = isVerified,
    mainNeighborhood = mainNeighborhood,
    city = city,
    createdAtTimestamp = createdAt.toEpochMilliseconds(),
    isActive = isActive,
)