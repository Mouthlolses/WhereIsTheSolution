package com.whereisthesolution.whereisihesolutionapp.repository

import com.whereisthesolution.whereisihesolutionapp.data.dao.UserDao
import com.whereisthesolution.whereisihesolutionapp.data.mappers.toDomain
import com.whereisthesolution.whereisihesolutionapp.data.mappers.toEntity
import com.whereisthesolution.whereisihesolutionapp.domain.model.user.User
import com.whereisthesolution.whereisihesolutionapp.network.api.UserApi
import com.whereisthesolution.whereisihesolutionapp.network.dto.AuthUserRequest
import com.whereisthesolution.whereisihesolutionapp.network.dto.RegisterUserRequest
import com.whereisthesolution.whereisihesolutionapp.session.SessionManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

interface UserRepository {

    suspend fun registerUser(request: RegisterUserRequest): Result<User>

    suspend fun authenticateUser(request: AuthUserRequest): Result<User>
    fun observeLoggedUser(): Flow<User?>

    suspend fun logout()

}


class UserRepositoryImpl(
    private val userDao: UserDao,
    private val sessionManager: SessionManager,
    private val userApi: UserApi
) : UserRepository {

    override suspend fun registerUser(
        request: RegisterUserRequest
    ): Result<User> {
        return try {

            val response = userApi.registerUser(request)

            val user = response.toDomain()

            userDao.insertUser(user.toEntity())

            Result.success(user)

        } catch (e: Exception) {

            println("REGISTER ERRO: ${e::class.simpleName}")
            println("REGISTER MENSAGEM: ${e.message}")

            Result.failure(e)
        }
    }

    override suspend fun authenticateUser(request: AuthUserRequest): Result<User> {
        return try {
            val response = userApi.authenticateUser(request)

            val user = response.user

            sessionManager.login(
                userId = user.id,
                token = response.token
            )

            userDao.insertUser(user.toEntity())

            Result.success(user)

        } catch (e: Exception) {
            println("LOGIN ERRO: ${e::class.simpleName}")
            println("LOGIN MENSAGEM: ${e.message}")
            Result.failure(e)

        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeLoggedUser(): Flow<User?> {
        return sessionManager.loggedUserId
            .flatMapLatest { userId ->
                if (userId == null) {
                    flowOf(null)
                } else {
                    userDao.observeUserById(userId)
                        .map { it?.toDomain() }
                }
            }
    }

    override suspend fun logout() {
        sessionManager.logout()
    }
}