package com.whereisthesolution.whereisihesolutionapp.di

import androidx.room.RoomDatabase
import com.whereisthesolution.whereisihesolutionapp.data.database.AppDatabase
import org.koin.dsl.module


val databaseModule = module {

    // Recebe o Builder já configurado com o Context do AndroidApp e constrói o banco
    single {
        get<RoomDatabase.Builder<AppDatabase>>().build()
    }

    // Injeta os DAOs normalmente
    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().postDao() }
}


