package com.gvelesiani.passmanager.diModules

import com.gvelesiani.passmanager.ui.addPassword.AddPasswordViewModel
import com.gvelesiani.passmanager.ui.checkPassword.CheckPasswordViewModel
import com.gvelesiani.passmanager.ui.home.HomeViewModel
import com.gvelesiani.passmanager.ui.passwords.PasswordsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        HomeViewModel()
    }
    viewModel {
        AddPasswordViewModel()
    }
    viewModel {
        CheckPasswordViewModel()
    }
    viewModel {
        PasswordsViewModel()
    }
}