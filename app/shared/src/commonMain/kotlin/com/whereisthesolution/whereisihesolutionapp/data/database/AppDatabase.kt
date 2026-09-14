package com.whereisthesolution.whereisihesolutionapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.whereisthesolution.whereisihesolutionapp.data.dao.PostDao
import com.whereisthesolution.whereisihesolutionapp.data.dao.UserDao
import com.whereisthesolution.whereisihesolutionapp.data.entity.PostEntity
import com.whereisthesolution.whereisihesolutionapp.data.entity.UserEntity


@Database(entities = [UserEntity::class, PostEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun postDao(): PostDao
}