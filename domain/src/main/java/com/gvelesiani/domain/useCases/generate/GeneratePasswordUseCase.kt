package com.gvelesiani.domain.useCases.generate

import com.gvelesiani.base.BaseUseCase
import com.gvelesiani.helpers.helpers.generatePassword.PasswordGeneratorHelper

class Password(val passLength: Int, val passwordProperties: String)
class GeneratePasswordUseCase(private val passwordGeneratorHelper: PasswordGeneratorHelper) :
    BaseUseCase<Password, String>() {
    override suspend fun invoke(params: Password): String {
        return passwordGeneratorHelper.generatePassword(
            params.passLength,
            params.passwordProperties
        )
    }
}