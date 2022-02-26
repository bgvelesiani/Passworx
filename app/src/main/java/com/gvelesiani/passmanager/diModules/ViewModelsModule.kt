package com.gvelesiani.passmanager.diModules

import com.gvelesiani.passmanager.ui.addPassword.AddPasswordViewModel
import com.gvelesiani.passmanager.ui.passwordGenerator.PasswordGeneratorViewModel
import com.gvelesiani.passmanager.ui.passwords.PasswordsViewModel
import com.gvelesiani.passmanager.ui.tools.SettingsViewModel
import com.gvelesiani.passmanager.ui.trash.PasswordTrashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        AddPasswordViewModel(get(), get(), get())
    }
    viewModel {
        SettingsViewModel()
    }
    viewModel {
        PasswordsViewModel(get(), get(), get())
    }

    viewModel {
        PasswordGeneratorViewModel(get(), get())
    }

    viewModel {
        PasswordTrashViewModel(get(), get())
    }

}