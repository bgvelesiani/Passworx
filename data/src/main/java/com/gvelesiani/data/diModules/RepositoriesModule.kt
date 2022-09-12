package com.gvelesiani.data.diModules

import com.gvelesiani.data.repositories.local.*
import com.gvelesiani.domain.repositories.*
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

object RepositoriesModule {
    fun load(): Module {
        return repositoriesModule
    }

    private val repositoriesModule = module {
        single {
            PasswordsRepositoryImpl(
                get()
            )
        } bind PasswordsRepository::class

        single {
            BackupRepositoryImpl(
                get()
            )
        } bind BackupRepository::class

        single {
            BiometricsRepositoryImpl(
                get()
            )
        } bind BiometricsRepository::class

        single {
            IntroRepositoryImpl(
                get()
            )
        } bind IntroRepository::class

        single {
            MasterPasswordRepositoryImpl(
                get()
            )
        } bind MasterPasswordRepository::class

        single {
            ScreenshotsRepositoryImpl(
                get()
            )
        } bind ScreenshotsRepository::class
    }
}