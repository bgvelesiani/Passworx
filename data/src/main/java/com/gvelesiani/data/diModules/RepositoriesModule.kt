package com.gvelesiani.data.diModules

import com.gvelesiani.data.repositories.local.AppColorsRepositoryImpl
import com.gvelesiani.data.repositories.local.BackupRepositoryImpl
import com.gvelesiani.data.repositories.local.BiometricsRepositoryImpl
import com.gvelesiani.data.repositories.local.IntroRepositoryImpl
import com.gvelesiani.data.repositories.local.MasterPasswordRepositoryImpl
import com.gvelesiani.data.repositories.local.PasswordsRepositoryImpl
import com.gvelesiani.data.repositories.local.ScreenshotsRepositoryImpl
import com.gvelesiani.domain.repositories.AppColorsRepository
import com.gvelesiani.domain.repositories.BackupRepository
import com.gvelesiani.domain.repositories.BiometricsRepository
import com.gvelesiani.domain.repositories.IntroRepository
import com.gvelesiani.domain.repositories.MasterPasswordRepository
import com.gvelesiani.domain.repositories.PasswordsRepository
import com.gvelesiani.domain.repositories.ScreenshotsRepository
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

        single {
            AppColorsRepositoryImpl(
                get()
            )
        } bind AppColorsRepository::class
    }
}