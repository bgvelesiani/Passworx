package com.gvelesiani.passworx.domain.useCases

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.helpers.generatePassword.PasswordGeneratorHelper

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