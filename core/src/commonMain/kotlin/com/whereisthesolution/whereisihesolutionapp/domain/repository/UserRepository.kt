package com.whereisthesolution.whereisihesolutionapp.domain.repository

import com.whereisthesolution.whereisihesolutionapp.domain.model.user.User
import com.whereisthesolution.whereisihesolutionapp.domain.model.user.UserCredential
import com.whereisthesolution.whereisihesolutionapp.domain.model.user.UserRegistration

interface UserRepository {

    suspend fun save(user: UserRegistration): User

    suspend fun findById(id: Long): User?

    suspend fun findByEmail(email: String): User?

    suspend fun listUsers(): List<User>

    suspend fun saveCredential(
        userId: Long,
        passwordHash: String
    )

    suspend fun findCredentialByUserId(
        userId: Long
    ): UserCredential?

}