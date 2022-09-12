package com.gvelesiani.domain.useCases.encryption

import com.gvelesiani.helpers.helpers.encryptPassword.PasswordEncryptionHelper

class DecryptPasswordUseCase(private val encryptionHelper: PasswordEncryptionHelper) :
    com.gvelesiani.base.BaseUseCase<String, String>() {
    override suspend fun invoke(params: String): String {
        return encryptionHelper.decrypt(params)
    }
}