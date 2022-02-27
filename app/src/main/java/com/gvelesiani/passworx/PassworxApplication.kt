package com.gvelesiani.passworx

import android.app.Application
import com.gvelesiani.passworx.diModules.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class PassworxApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(provideKoinModules())
        }
    }

    private fun provideKoinModules(): List<Module> {
        return listOf(
            viewModelsModule,
            helpersModule,
            useCasesModule,
            localStorageModule,
            repositoryModule
        )
    }
}
