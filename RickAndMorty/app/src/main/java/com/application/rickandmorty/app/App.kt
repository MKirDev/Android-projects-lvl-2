package com.application.rickandmorty.app

import android.app.Application
import android.util.Log
import com.application.rickandmorty.di.appModule
import com.application.rickandmorty.di.dataModule
import com.application.rickandmorty.di.domainModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            modules(listOf(appModule, domainModule, dataModule))
        }
    }
}