package com.gvelesiani.passworx.diModules

import com.gvelesiani.passworx.helpers.encryptPassword.PasswordEncryptionHelper
import com.gvelesiani.passworx.helpers.encryptPassword.PasswordEncryptionHelperImpl
import com.gvelesiani.passworx.helpers.generatePassword.PasswordGeneratorHelper
import com.gvelesiani.passworx.helpers.generatePassword.PasswordGeneratorHelperImpl
import com.gvelesiani.passworx.helpers.resourceProvider.ResourceHelper
import com.gvelesiani.passworx.helpers.resourceProvider.ResourceHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

val helpersModule = module {
    single { PasswordEncryptionHelperImpl(get()) } bind PasswordEncryptionHelper::class
    single { PasswordGeneratorHelperImpl() } bind PasswordGeneratorHelper::class
    single { ResourceHelperImpl(androidContext()) } bind ResourceHelper::class
}