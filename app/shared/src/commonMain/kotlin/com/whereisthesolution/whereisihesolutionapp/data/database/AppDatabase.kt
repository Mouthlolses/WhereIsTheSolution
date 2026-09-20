package com.whereisthesolution.whereisihesolutionapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.whereisthesolution.whereisihesolutionapp.data.converters.RoomConverters
import com.whereisthesolution.whereisihesolutionapp.data.dao.PostDao
import com.whereisthesolution.whereisihesolutionapp.data.dao.UserDao
import com.whereisthesolution.whereisihesolutionapp.data.entity.PostEntity
import com.whereisthesolution.whereisihesolutionapp.data.entity.UserEntity


@Database(entities = [UserEntity::class, PostEntity::class], version = 1, exportSchema = true)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun postDao(): PostDao
}