package com.whereisthesolution.whereisihesolutionapp.di

import com.whereisthesolution.whereisihesolutionapp.network.api.UserApi
import com.whereisthesolution.whereisihesolutionapp.network.createHttpClient
import com.whereisthesolution.whereisihesolutionapp.session.SessionManager
import org.koin.dsl.module

val networkModule = module {

    single {
        createHttpClient(get())
    }

    single {
        UserApi(get())
    }
}