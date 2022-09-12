package com.gvelesiani.domain.useCases.encryption

import com.gvelesiani.base.BaseUseCase
import com.gvelesiani.helpers.helpers.encryptPassword.PasswordEncryptionHelper

class DecryptPasswordUseCase(private val encryptionHelper: PasswordEncryptionHelper) :
    BaseUseCase<String, String>() {
    override suspend fun invoke(params: String): String {
        return encryptionHelper.decrypt(params)
    }
}