package com.gvelesiani.passmanager.diModules

import com.gvelesiani.passmanager.domain.useCases.AddNewPasswordUseCase
import com.gvelesiani.passmanager.domain.useCases.GeneratePasswordUseCase
import org.koin.dsl.module

val useCasesModule = module {
    factory {
        GeneratePasswordUseCase(get())
    }

    factory {
        AddNewPasswordUseCase(get())
    }
}