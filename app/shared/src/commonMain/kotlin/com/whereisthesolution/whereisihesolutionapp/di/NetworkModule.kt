package com.whereisthesolution.whereisihesolutionapp.di

import com.whereisthesolution.whereisihesolutionapp.network.api.UserApi
import com.whereisthesolution.whereisihesolutionapp.network.createHttpClient
import org.koin.dsl.module

val networkModule = module {

    single {
        createHttpClient()
    }

    single {
        UserApi(get(),)
    }
}