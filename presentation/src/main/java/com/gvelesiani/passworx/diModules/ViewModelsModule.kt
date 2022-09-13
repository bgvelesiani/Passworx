package com.gvelesiani.passworx.diModules

import com.gvelesiani.passworx.ui.MainVM
import com.gvelesiani.passworx.ui.addPassword.AddPasswordVM
import com.gvelesiani.passworx.ui.backupAndRestore.BackupAndRestoreVM
import com.gvelesiani.passworx.ui.updatePassword.UpdatePasswordVM
import com.gvelesiani.passworx.ui.favorites.PasswordFavoritesVM
import com.gvelesiani.passworx.ui.intro.IntroVM
import com.gvelesiani.passworx.ui.intro.thirdStep.ThirdStepVM
import com.gvelesiani.passworx.ui.masterPassword.MasterPasswordAVM
import com.gvelesiani.passworx.ui.masterPassword.fragments.MasterPasswordVM
import com.gvelesiani.passworx.ui.masterPassword.fragments.changeMasterPassword.ChangeMasterPasswordVM
import com.gvelesiani.passworx.ui.masterPassword.fragments.createMasterPassword.CreateMasterPasswordVM
import com.gvelesiani.passworx.ui.passwordDetails.PasswordDetailsVM
import com.gvelesiani.passworx.ui.passwordGenerator.PasswordGeneratorVM
import com.gvelesiani.passworx.ui.passwords.PasswordsVM
import com.gvelesiani.passworx.ui.settings.SettingsVM
import com.gvelesiani.passworx.ui.trash.PasswordTrashVM
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
            PasswordsVM(get(), get(), get(), get(), get())
        }

        viewModel {
            PasswordGeneratorVM(get(), get())
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
            MasterPasswordAVM(get(), get())
        }

        viewModel {
            MainVM(get())
        }

        viewModel {
            IntroVM(get())
        }

        viewModel {
            UpdatePasswordVM(get(), get(), get())
        }

        viewModel {
            BackupAndRestoreVM(get(), get(), get(), get(), get())
        }
    }
}