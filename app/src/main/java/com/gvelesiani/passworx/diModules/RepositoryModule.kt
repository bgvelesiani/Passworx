package com.gvelesiani.passworx.diModules

import com.gvelesiani.passworx.data.repository.Repository
import com.gvelesiani.passworx.data.repository.RepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    factory {
        RepositoryImpl(
            get()
        )
    } bind Repository::class
}