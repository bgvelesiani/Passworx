package com.gvelesiani.passworx.diModules

import com.gvelesiani.passworx.ui.addPassword.AddPasswordVM
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
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        AddPasswordVM(get(), get(), get())
    }
    viewModel {
        SettingsVM()
    }
    viewModel {
        PasswordsVM(get(), get(), get(), get())
    }

    viewModel {
        PasswordGeneratorVM(get(), get())
    }

    viewModel {
        PasswordTrashVM(get(), get())
    }

    viewModel {
        PasswordDetailsVM(get())
    }

    viewModel {
        CreateMasterPasswordVM(get(), get(), get())
    }

    viewModel {
        MasterPasswordVM(get(), get())
    }

    viewModel {
        ChangeMasterPasswordVM(get(), get(), get(), get())
    }

    viewModel {
        MasterPasswordAVM(get())
    }
}