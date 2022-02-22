package com.gvelesiani.passmanager.domain.useCases

import com.gvelesiani.passmanager.base.BaseUseCase
import com.gvelesiani.passmanager.helpers.generatePassword.PasswordGeneratorHelper

class Password(val passLength: Int, val passwordProperties: String)
class GeneratePasswordUseCase(private val passwordGeneratorHelper: PasswordGeneratorHelper) :
    BaseUseCase<Password, String>() {
    override suspend fun run(params: Password): String {
        return passwordGeneratorHelper.generatePassword(
            params.passLength,
            params.passwordProperties
        )
    }
}