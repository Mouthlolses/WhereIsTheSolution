package com.whereisthesolution.whereisihesolutionapp.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val email: String,
    val password: String,
    val cpfHash: String?,
    val avatarUrl: String?,
    val privacyLevel: String?,
    val reputationScore: Int?,
    val isVerified: Boolean,
    val mainNeighborhood: String?,
    val city: String?,
    val createdAtTimestamp: Long,
    val isActive: Boolean
)



data class PostWithUser(
    @Embedded
    val post: PostEntity,

    @Relation(
        parentColumn = "userId", // Coluna que está dentro do PostEntity
        entityColumn = "id"      // Coluna correspondente no UserEntity
    )
    val user: UserEntity
)