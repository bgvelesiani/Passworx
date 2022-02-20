package com.gvelesiani.passmanager.diModules

import com.gvelesiani.passmanager.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        HomeViewModel()
    }
}