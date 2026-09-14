package com.whereisthesolution.whereisihesolutionapp.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.whereisthesolution.whereisihesolutionapp.data.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidDatabaseModule = module {
    single<RoomDatabase.Builder<AppDatabase>> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "denuncias_urbanas.db"
        )
    }
}