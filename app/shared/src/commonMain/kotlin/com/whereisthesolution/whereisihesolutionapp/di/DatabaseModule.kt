package com.whereisthesolution.whereisihesolutionapp.di

import androidx.room.RoomDatabase
import com.whereisthesolution.whereisihesolutionapp.data.database.AppDatabase
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.home.HomeViewModel
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.login.LoginViewModel
import com.whereisthesolution.whereisihesolutionapp.repository.UserRepository
import com.whereisthesolution.whereisihesolutionapp.repository.UserRepositoryImpl
import com.whereisthesolution.whereisihesolutionapp.session.SessionManager
import org.koin.dsl.module


val databaseModule = module {

    // Recebe o Builder já configurado com o Context do AndroidApp e constrói o banco
    single {
        get<RoomDatabase.Builder<AppDatabase>>().build()
    }

    // Injeta os DAOs normalmente

    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().postDao() }


    single { SessionManager() }

    single<UserRepository> {
        UserRepositoryImpl(
            userDao = get(),
            sessionManager = get()
        )
    }

    single {
        LoginViewModel(
            repository = get()
        )
    }

    single {
        HomeViewModel(
            repository = get()
        )
    }
}


