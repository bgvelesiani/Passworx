package com.gvelesiani.passmanager

import android.app.Application
import com.gvelesiani.passmanager.diModules.helpersModule
import com.gvelesiani.passmanager.diModules.useCasesModule
import com.gvelesiani.passmanager.diModules.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class PassManagerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(provideKoinModules())
        }
    }

    private fun provideKoinModules(): List<Module> {
        return listOf(
            viewModelsModule, helpersModule, useCasesModule
        )
    }
}
