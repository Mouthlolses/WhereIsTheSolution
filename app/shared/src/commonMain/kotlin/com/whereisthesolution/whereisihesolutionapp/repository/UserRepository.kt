package com.whereisthesolution.whereisihesolutionapp.repository

import com.whereisthesolution.whereisihesolutionapp.data.dao.UserDao
import com.whereisthesolution.whereisihesolutionapp.data.mappers.toDomain
import com.whereisthesolution.whereisihesolutionapp.data.mappers.toEntity
import com.whereisthesolution.whereisihesolutionapp.domain.model.user.User
import com.whereisthesolution.whereisihesolutionapp.network.api.UserApi
import com.whereisthesolution.whereisihesolutionapp.network.dto.RegisterUserRequest
import com.whereisthesolution.whereisihesolutionapp.session.SessionManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

interface UserRepository {

    suspend fun registerUser(request: RegisterUserRequest): Result<User>
    fun observeLoggedUser(): Flow<User?>

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
            println("REGISTER 1: chamando API")

            val response = userApi.registerUser(request)

            println("REGISTER 2: API respondeu")

            val user = response.toDomain()

            println("REGISTER 3: convertido para User")

            userDao.insertUser(user.toEntity())

            println("REGISTER 4: salvo no Room")

            sessionManager.login(user.id)

            println("REGISTER 5: sessão salva")

            Result.success(user)

        } catch (e: Exception) {
            println("REGISTER ERRO: ${e::class.simpleName}")
            println("REGISTER MENSAGEM: ${e.message}")
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
}