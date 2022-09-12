package com.gvelesiani.domain.useCases.backup

import com.google.gson.Gson
import com.gvelesiani.domain.common.fromJson
import com.gvelesiani.helpers.helpers.encryptPassword.PasswordEncryptionHelper
import com.gvelesiani.domain.model.PasswordModel

class GetPasswordsFromStringUseCase(private val encryptionHelper: PasswordEncryptionHelper) :
    com.gvelesiani.base.BaseUseCase<String, List<PasswordModel>>() {
    override suspend fun invoke(params: String): List<PasswordModel> {
        val decryptedParams = encryptionHelper.decrypt(params)
        return Gson().fromJson(decryptedParams)
    }
}