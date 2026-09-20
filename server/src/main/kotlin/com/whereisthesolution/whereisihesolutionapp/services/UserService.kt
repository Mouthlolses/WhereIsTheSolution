package com.whereisthesolution.whereisihesolutionapp.services

import com.whereisthesolution.whereisihesolutionapp.domain.model.user.User
import com.whereisthesolution.whereisihesolutionapp.domain.model.user.UserRegistration
import com.whereisthesolution.whereisihesolutionapp.domain.repository.UserRepository
import com.whereisthesolution.whereisihesolutionapp.security.PasswordHasher

class UserService(
    private val userRepository: UserRepository,
    private val passwordHasher: PasswordHasher
) {

    suspend fun save(
        name: String,
        email: String,
        password: String
    ): User {

        val user = User(
            id = 0,
            name = name,
            email = email
        )

        val registration = UserRegistration(
            user = user,
            passwordHash = passwordHasher.hash(password)
        )

        return userRepository.save(registration)
    }

    suspend fun findById(id: Long): User? {
        return userRepository.findById(id)
    }

    suspend fun listUsers(): List<User> {
        return userRepository.listUsers()
    }

    suspend fun login(
        email: String,
        password: String
    ): Boolean {

        val user = userRepository.findByEmail(email)
            ?: return false

        val credential = userRepository.findCredentialByUserId(user.id)
            ?: return false

        return passwordHasher.verify(
            password = password,
            hash = credential.passwordHash
        )
    }
}