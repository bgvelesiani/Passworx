package com.gvelesiani.passmanager.diModules

import com.gvelesiani.passmanager.data.repository.Repository
import com.gvelesiani.passmanager.data.repository.RepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    factory {
        RepositoryImpl(
            get()
        )
    } bind Repository::class
}