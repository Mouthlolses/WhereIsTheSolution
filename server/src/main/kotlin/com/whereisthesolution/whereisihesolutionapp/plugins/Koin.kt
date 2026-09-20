package com.whereisthesolution.whereisihesolutionapp.plugins

import com.whereisthesolution.whereisihesolutionapp.di.appModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(appModule)
    }
}