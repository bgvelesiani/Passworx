package com.gvelesiani.passworx.diModules

import com.gvelesiani.passworx.data.repositories.local.*
import com.gvelesiani.passworx.domain.repositories.IntroRepository
import com.gvelesiani.passworx.domain.repositories.local.*
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoriesModule = module {
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