package com.gvelesiani.passmanager.diModules

import com.gvelesiani.passmanager.ui.addPassword.AddPasswordViewModel
import com.gvelesiani.passmanager.ui.home.HomeViewModel
import com.gvelesiani.passmanager.ui.passwordGenerator.PasswordGeneratorViewModel
import com.gvelesiani.passmanager.ui.passwords.PasswordsViewModel
import com.gvelesiani.passmanager.ui.tools.ToolsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        AddPasswordViewModel(get(), get(), get())
    }
    viewModel {
        ToolsViewModel()
    }
    viewModel {
        PasswordsViewModel()
    }

    viewModel {
        PasswordGeneratorViewModel(get(), get())
    }
}