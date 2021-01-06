package br.com.fornaro.mesanews

import android.app.Application
import br.com.fornaro.mesanews.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startDependencyInjection()
    }

    private fun startDependencyInjection() = startKoin {
        androidContext(this@MainApplication)
        modules(allModules)
    }
}