package com.gvelesiani.helpers.diModule

import com.gvelesiani.helpers.helpers.biometrics.BiometricsHelper
import com.gvelesiani.helpers.helpers.biometrics.BiometricsHelperImpl
import com.gvelesiani.helpers.helpers.encryptPassword.PasswordEncryptionHelper
import com.gvelesiani.helpers.helpers.encryptPassword.PasswordEncryptionHelperImpl
import com.gvelesiani.helpers.helpers.generatePassword.PasswordGeneratorHelper
import com.gvelesiani.helpers.helpers.generatePassword.PasswordGeneratorHelperImpl
import com.gvelesiani.helpers.helpers.hashPassword.PasswordHashHelper
import com.gvelesiani.helpers.helpers.hashPassword.PasswordHashHelperImpl
import com.gvelesiani.helpers.helpers.resourceProvider.ResourceHelper
import com.gvelesiani.helpers.helpers.resourceProvider.ResourceHelperImpl
import com.gvelesiani.helpers.helpers.validateMasterPassword.MasterPasswordValidatorHelper
import com.gvelesiani.helpers.helpers.validateMasterPassword.MasterPasswordValidatorHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

object HelpersModule {
    fun load(): Module {
        return helpersModule
    }

    private val helpersModule = module {
        single { PasswordEncryptionHelperImpl() } bind PasswordEncryptionHelper::class
        single { PasswordGeneratorHelperImpl() } bind PasswordGeneratorHelper::class
        single { ResourceHelperImpl(androidContext()) } bind ResourceHelper::class
        single { PasswordHashHelperImpl() } bind PasswordHashHelper::class
        single { MasterPasswordValidatorHelperImpl() } bind MasterPasswordValidatorHelper::class
        single { BiometricsHelperImpl(get()) } bind BiometricsHelper::class
    }
}