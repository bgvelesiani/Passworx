package com.gvelesiani.passworx.domain.useCases

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.helpers.encryptPassword.PasswordEncryptionHelper

class DecryptPasswordUseCase(private val encryptionHelper: PasswordEncryptionHelper) :
    BaseUseCase<String, String>() {
    override suspend fun invoke(params: String): String {
        return encryptionHelper.decrypt(params)
    }
}