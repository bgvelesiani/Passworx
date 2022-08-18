package com.gvelesiani.passworx.domain.useCases

import com.google.gson.Gson
import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.common.fromJson
import com.gvelesiani.passworx.domain.model.PasswordModel
import com.gvelesiani.passworx.helpers.encryptPassword.PasswordEncryptionHelper

class GetPasswordsFromStringUseCase(private val encryptionHelper: PasswordEncryptionHelper) :
    BaseUseCase<String, List<PasswordModel>>() {
    override suspend fun invoke(params: String): List<PasswordModel> {
        val decryptedParams = encryptionHelper.decrypt(params)
        return Gson().fromJson(decryptedParams)
    }
}