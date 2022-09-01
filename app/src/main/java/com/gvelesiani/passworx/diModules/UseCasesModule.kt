package com.gvelesiani.passworx.diModules

import com.gvelesiani.passworx.domain.useCases.backup.GetPasswordsFromStringUseCase
import com.gvelesiani.passworx.domain.useCases.biometrics.AllowBiometricsUseCase
import com.gvelesiani.passworx.domain.useCases.biometrics.GetBiometricsAllowingStatusUserCase
import com.gvelesiani.passworx.domain.useCases.encryption.DecryptPasswordUseCase
import com.gvelesiani.passworx.domain.useCases.generate.GeneratePasswordUseCase
import com.gvelesiani.passworx.domain.useCases.intro.CheckIfIntroIsFinishedUseCase
import com.gvelesiani.passworx.domain.useCases.intro.FinishIntroUseCase
import com.gvelesiani.passworx.domain.useCases.masterPassword.CreateOrChangeMasterPasswordUseCase
import com.gvelesiani.passworx.domain.useCases.masterPassword.GetMasterPasswordUseCase
import com.gvelesiani.passworx.domain.useCases.passwords.*
import com.gvelesiani.passworx.domain.useCases.screenshots.GetTakingScreenshotsStatusUseCase
import com.gvelesiani.passworx.domain.useCases.screenshots.PreventTakingScreenshotsUseCase
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

    factory {
        SearchPasswordsUseCase(get())
    }

    factory {
        CreateOrChangeMasterPasswordUseCase(get())
    }

    factory {
        GetMasterPasswordUseCase(get())
    }

    factory {
        DecryptPasswordUseCase(get())
    }

    factory {
        GetFavoritePasswordsUseCase(get())
    }

    factory {
        GetTakingScreenshotsStatusUseCase(get())
    }

    factory {
        PreventTakingScreenshotsUseCase(get())
    }

    factory {
        GetBiometricsAllowingStatusUserCase(get())
    }

    factory {
        AllowBiometricsUseCase(get())
    }

    factory {
        FinishIntroUseCase(get())
    }

    factory {
        CheckIfIntroIsFinishedUseCase(get())
    }

    factory {
        GetPasswordsFromStringUseCase(get())
    }

    factory {
        UpdatePasswordUseCase(get())
    }
}