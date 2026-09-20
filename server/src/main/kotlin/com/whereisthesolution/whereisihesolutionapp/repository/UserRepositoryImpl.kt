package com.whereisthesolution.whereisihesolutionapp.repository

import com.whereisthesolution.whereisihesolutionapp.database.tables.UsersTable
import com.whereisthesolution.whereisihesolutionapp.domain.model.user.User
import com.whereisthesolution.whereisihesolutionapp.domain.model.user.UserCredential
import com.whereisthesolution.whereisihesolutionapp.domain.model.user.UserRegistration
import com.whereisthesolution.whereisihesolutionapp.domain.repository.UserRepository
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update

class UserRepositoryImpl : UserRepository {

    override suspend fun save(user: UserRegistration): User {
        return transaction {
            val id = UsersTable.insert {
                it[name] = user.user.name
                it[email] = user.user.email
                it[passwordHash] = user.passwordHash
            } get UsersTable.id

            user.user.copy(id = id)
        }
    }

    override suspend fun findById(id: Long): User? {
        return transaction {
            UsersTable
                .selectAll()
                .where { UsersTable.id eq id }
                .map {
                    User(
                        id = it[UsersTable.id],
                        name = it[UsersTable.name],
                        email = it[UsersTable.email]
                    )
                }
                .singleOrNull()
        }
    }

    override suspend fun findByEmail(email: String): User? {
        return transaction {
            UsersTable
                .selectAll()
                .where { UsersTable.email eq email }
                .map {
                    User(
                        id = it[UsersTable.id],
                        name = it[UsersTable.name],
                        email = it[UsersTable.email]
                    )
                }
                .singleOrNull()
        }
    }

    override suspend fun listUsers(): List<User> {
        return transaction {
            UsersTable
                .selectAll()
                .map {
                    User(
                        id = it[UsersTable.id],
                        name = it[UsersTable.name],
                        email = it[UsersTable.email]
                    )
                }
        }
    }

    override suspend fun saveCredential(
        userId: Long,
        passwordHash: String
    ) {
        transaction {
            UsersTable.update(
                where = { UsersTable.id eq userId }
            ) {
                it[UsersTable.passwordHash] = passwordHash
            }
        }
    }

    override suspend fun findCredentialByUserId(
        userId: Long
    ): UserCredential? {
        return transaction {
            UsersTable
                .selectAll()
                .where { UsersTable.id eq userId }
                .map {
                    UserCredential(
                        userId = it[UsersTable.id],
                        passwordHash = it[UsersTable.passwordHash]
                    )
                }
                .singleOrNull()
        }
    }
}