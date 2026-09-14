package com.whereisthesolution.whereisihesolutionapp.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

// 1. Agrupe todos os módulos do Koin da sua aplicação
val appModules = module {
    includes(
        databaseModule
        // viewModelModule
    )
}

// 2. Função de inicialização exposta para as plataformas (Android / iOS)
fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(appModules)
    }
}