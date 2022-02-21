package com.gvelesiani.passmanager.diModules

import com.gvelesiani.passmanager.domain.useCases.GeneratePasswordUseCase
import org.koin.dsl.module

val useCasesModule = module {
    single {
        GeneratePasswordUseCase(get())
    }
}