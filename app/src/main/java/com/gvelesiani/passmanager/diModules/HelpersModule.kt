package com.gvelesiani.passmanager.diModules

import com.gvelesiani.passmanager.helpers.encryptPassword.PasswordEncryptionHelper
import com.gvelesiani.passmanager.helpers.encryptPassword.PasswordEncryptionHelperImpl
import com.gvelesiani.passmanager.helpers.generatePassword.PasswordGeneratorHelper
import com.gvelesiani.passmanager.helpers.generatePassword.PasswordGeneratorHelperImpl
import com.gvelesiani.passmanager.helpers.resourceProvider.ResourceHelper
import com.gvelesiani.passmanager.helpers.resourceProvider.ResourceHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

val helpersModule = module {
    single { PasswordEncryptionHelperImpl(get()) } bind PasswordEncryptionHelper::class
    single { PasswordGeneratorHelperImpl() } bind PasswordGeneratorHelper::class
    single { ResourceHelperImpl(androidContext()) } bind ResourceHelper::class
}