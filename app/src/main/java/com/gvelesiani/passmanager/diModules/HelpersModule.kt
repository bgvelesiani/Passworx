package com.gvelesiani.passmanager.diModules

import com.gvelesiani.passmanager.helpers.PasswordHashHelper
import com.gvelesiani.passmanager.helpers.PasswordHashHelperImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val helpersModule = module {
    single { PasswordHashHelperImpl() } bind PasswordHashHelper::class
}