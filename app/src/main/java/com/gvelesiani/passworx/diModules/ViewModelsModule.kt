package com.gvelesiani.passworx.diModules

import com.gvelesiani.passworx.ui.addPassword.AddPasswordViewModel
import com.gvelesiani.passworx.ui.passwordGenerator.PasswordGeneratorViewModel
import com.gvelesiani.passworx.ui.passwords.PasswordsViewModel
import com.gvelesiani.passworx.ui.tools.SettingsViewModel
import com.gvelesiani.passworx.ui.trash.PasswordTrashViewModel
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