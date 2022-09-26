package com.gvelesiani.passworx.diModules

import com.gvelesiani.passworx.uiCompose.updatePassword.UpdatePasswordVM
import com.gvelesiani.passworx.uiCompose.MainVM
import com.gvelesiani.passworx.uiCompose.addPassword.AddPasswordVM
import com.gvelesiani.passworx.uiCompose.backupAndRestore.BackupAndRestoreVM
import com.gvelesiani.passworx.uiCompose.favorites.PasswordFavoritesVM
import com.gvelesiani.passworx.uiCompose.intro.IntroVM
import com.gvelesiani.passworx.uiCompose.intro.thirdStep.ThirdStepVM
import com.gvelesiani.passworx.uiCompose.masterPassword.MasterPasswordVM
import com.gvelesiani.passworx.uiCompose.masterPassword.changeMasterPassword.ChangeMasterPasswordVM
import com.gvelesiani.passworx.uiCompose.masterPassword.createMasterPassword.CreateMasterPasswordVM
import com.gvelesiani.passworx.uiCompose.passwordDetails.PasswordDetailsVM
import com.gvelesiani.passworx.uiCompose.passwordGenerator.PasswordGeneratorVM
import com.gvelesiani.passworx.uiCompose.passwords.PasswordsVM
import com.gvelesiani.passworx.uiCompose.settings.SettingsVM
import com.gvelesiani.passworx.uiCompose.trash.PasswordTrashVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object ViewModelsModule {
    fun load(): Module {
        return viewModelsModule
    }

    private val viewModelsModule = module {
        viewModel {
            AddPasswordVM(get(), get(), get())
        }

        viewModel {
            SettingsVM(get(), get(), get(), get())
        }

        viewModel {
            ThirdStepVM(get(), get())
        }

        viewModel {
            PasswordsVM(get(), get(), get(), get()) //, get())
        }

        viewModel {
            PasswordGeneratorVM(get())
        }

        viewModel {
            PasswordTrashVM(get(), get(), get(), get())
        }

        viewModel {
            PasswordDetailsVM(get())
        }

        viewModel {
            PasswordFavoritesVM(get(), get(), get(), get(), get())
        }

        viewModel {
            CreateMasterPasswordVM(get(), get(), get())
        }

        viewModel {
            MasterPasswordVM(get(), get(), get(), get())
        }

        viewModel {
            ChangeMasterPasswordVM(get(), get(), get(), get())
        }

        viewModel {
            MainVM(get(), get(), get())
        }

        viewModel {
            IntroVM(get())
        }

        viewModel {
            UpdatePasswordVM(get(), get())
        }

        viewModel {
            BackupAndRestoreVM(get(), get(), get(), get(), get())
        }
    }
}