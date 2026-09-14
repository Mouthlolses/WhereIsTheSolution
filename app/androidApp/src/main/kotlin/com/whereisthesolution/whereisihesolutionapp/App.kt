package com.whereisthesolution.whereisihesolutionapp

import android.app.Application
import com.whereisthesolution.whereisihesolutionapp.di.androidDatabaseModule
import com.whereisthesolution.whereisihesolutionapp.di.databaseModule
import com.whereisthesolution.whereisihesolutionapp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin{
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                androidDatabaseModule,
                databaseModule
            )
        }
    }
}