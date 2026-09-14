package com.whereisthesolution.whereisihesolutionapp.repository

import com.whereisthesolution.whereisihesolutionapp.data.dao.UserDao
import com.whereisthesolution.whereisihesolutionapp.data.entity.UserEntity

interface UserRepository {

    suspend fun registerUser(user: UserEntity): Result<Unit>

}


class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun registerUser(user: UserEntity): Result<Unit> {
        return try {
            userDao.insertUser(user)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}