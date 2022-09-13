package com.gvelesiani.passworx

import android.app.Application
import com.gvelesiani.data.diModules.LocalStorageModule
import com.gvelesiani.data.diModules.RepositoriesModule
import com.gvelesiani.domain.diModules.UseCaseModule
import com.gvelesiani.helpers.diModule.HelpersModule
import com.gvelesiani.passworx.diModules.ViewModelsModule
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
            ViewModelsModule.load(),
            HelpersModule.load(),
            UseCaseModule.load(),
            LocalStorageModule.load(),
            RepositoriesModule.load()
        )
    }
}
