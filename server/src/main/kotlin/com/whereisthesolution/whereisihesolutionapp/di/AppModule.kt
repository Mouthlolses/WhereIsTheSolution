package com.whereisthesolution.whereisihesolutionapp.di


import com.whereisthesolution.whereisihesolutionapp.domain.repository.PostRepository
import com.whereisthesolution.whereisihesolutionapp.domain.repository.UserRepository
import com.whereisthesolution.whereisihesolutionapp.repository.PostRepositoryImpl
import com.whereisthesolution.whereisihesolutionapp.repository.UserRepositoryImpl
import com.whereisthesolution.whereisihesolutionapp.security.JwtService
import com.whereisthesolution.whereisihesolutionapp.security.PasswordHasher
import com.whereisthesolution.whereisihesolutionapp.services.PostService
import com.whereisthesolution.whereisihesolutionapp.services.UserService
import com.whereisthesolution.whereisihesolutionapp.storage.ImageStorage
import com.whereisthesolution.whereisihesolutionapp.storage.SupabaseImageStorage
import com.whereisthesolution.whereisihesolutionapp.storage.config.SupabaseConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.koin.dsl.module

val appModule = module {

    single {
        PasswordHasher()
    }

    single<UserRepository> {
        UserRepositoryImpl()
    }

    single<PostRepository> {
        PostRepositoryImpl()
    }

    single {
        SupabaseConfig.fromEnvironment()
    }

    single {
        HttpClient(CIO)
    }

    single<ImageStorage> {
        SupabaseImageStorage(
            client = get(),
            config = get()
        )
    }

    single { JwtService() }

    single {
        UserService(
            userRepository = get(),
            passwordHasher = get()
        )
    }
    single {
        PostService(
            postRepository = get()
        )
    }
}