package com.gvelesiani.passmanager.diModules

import com.gvelesiani.passmanager.domain.useCases.*
import org.koin.dsl.module

val useCasesModule = module {
    factory {
        GeneratePasswordUseCase(get())
    }

    factory {
        AddNewPasswordUseCase(get())
    }

    factory {
        GetPasswordsUseCase(get())
    }

    factory {
        UpdateFavoriteStateUseCase(get())
    }

    factory {
        UpdateItemTrashStateUseCase(get())
    }

    factory {
        DeletePasswordUseCase(get())
    }
}