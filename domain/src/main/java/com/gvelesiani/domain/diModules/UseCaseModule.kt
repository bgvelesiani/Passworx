package com.gvelesiani.domain.diModules

import com.gvelesiani.domain.useCases.backup.GetPasswordsFromStringUseCase
import com.gvelesiani.domain.useCases.biometrics.AllowBiometricsUseCase
import com.gvelesiani.domain.useCases.biometrics.GetBiometricsAllowingStatusUserCase
import com.gvelesiani.domain.useCases.encryption.DecryptPasswordUseCase
import com.gvelesiani.domain.useCases.generate.GeneratePasswordUseCase
import com.gvelesiani.domain.useCases.intro.CheckIfIntroIsFinishedUseCase
import com.gvelesiani.domain.useCases.passwords.*
import com.gvelesiani.domain.useCases.intro.FinishIntroUseCase
import com.gvelesiani.domain.useCases.masterPassword.CreateOrChangeMasterPasswordUseCase
import com.gvelesiani.domain.useCases.masterPassword.GetMasterPasswordUseCase
import com.gvelesiani.domain.useCases.screenshots.GetTakingScreenshotsStatusUseCase
import com.gvelesiani.domain.useCases.screenshots.PreventTakingScreenshotsUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

object UseCaseModule {
    fun load(): Module {
        return useCasesModule
    }

    private val useCasesModule = module {
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
}