package com.whereisthesolution.whereisihesolutionapp.data.mappers

import com.whereisthesolution.whereisihesolutionapp.data.entity.UserEntity
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.PrivacyLevel
import com.whereisthesolution.whereisihesolutionapp.domain.model.user.User

// Converter Entity do Room para o Model do Core
fun UserEntity.toUser() = User(
    id = id,
    name = name,
    email = email,
    cpfHash = cpfHash,
    avatarUrl = avatarUrl,
    privacyLevel = PrivacyLevel.valueOf(privacyLevel),
    reputationScore = reputationScore,
    isVerified = isVerified,
    mainNeighborhood = mainNeighborhood,
    city = city,
    isActive = isActive
)

// Converter Entity do Room para o Model do Core
fun User.toEntity() = UserEntity(
    id = id,
    name = name,
    email = email,
    cpfHash = cpfHash,
    avatarUrl = avatarUrl,
    privacyLevel = privacyLevel.name,
    reputationScore = reputationScore,
    isVerified = isVerified,
    mainNeighborhood = mainNeighborhood,
    city = city,
    createdAtTimestamp = createdAt.toEpochMilli(),
    isActive = isActive
)