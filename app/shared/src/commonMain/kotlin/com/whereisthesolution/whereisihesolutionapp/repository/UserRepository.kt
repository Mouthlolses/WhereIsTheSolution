package com.whereisthesolution.whereisihesolutionapp.repository

import com.whereisthesolution.whereisihesolutionapp.data.dao.UserDao
import com.whereisthesolution.whereisihesolutionapp.data.entity.UserEntity
import com.whereisthesolution.whereisihesolutionapp.session.SessionManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface UserRepository {

    suspend fun registerUser(user: UserEntity): Result<Unit>
    fun observeLoggedUser(): Flow<UserEntity?>

}


class UserRepositoryImpl(
    private val userDao: UserDao,
    private val sessionManager: SessionManager
) : UserRepository {

    override suspend fun registerUser(user: UserEntity): Result<Unit> {
        return try {
            userDao.insertUser(user)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun observeLoggedUser(): Flow<UserEntity?> {
        val userId = sessionManager.getLoggedUserId()
            ?: return flowOf(null)

        return userDao.observeUserById(userId)
    }

}