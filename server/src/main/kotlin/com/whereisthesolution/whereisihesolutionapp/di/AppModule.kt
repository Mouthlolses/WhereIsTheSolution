package com.whereisthesolution.whereisihesolutionapp.di


import com.whereisthesolution.whereisihesolutionapp.domain.repository.UserRepository
import com.whereisthesolution.whereisihesolutionapp.repository.UserRepositoryImpl
import com.whereisthesolution.whereisihesolutionapp.security.PasswordHasher
import com.whereisthesolution.whereisihesolutionapp.services.UserService
import org.koin.dsl.module

val appModule = module {

    single {
        PasswordHasher()
    }

    single<UserRepository> {
        UserRepositoryImpl()
    }

    single {
        UserService(
            userRepository = get(),
            passwordHasher = get()
        )
    }
}