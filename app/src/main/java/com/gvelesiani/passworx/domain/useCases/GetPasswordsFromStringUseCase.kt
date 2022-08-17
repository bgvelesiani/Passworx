package com.gvelesiani.passworx.domain.useCases

import com.google.gson.Gson
import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.common.fromJson
import com.gvelesiani.passworx.domain.model.PasswordModel

class GetPasswordsFromStringUseCase : BaseUseCase<String, List<PasswordModel>>() {
    override suspend fun invoke(params: String): List<PasswordModel> {
        return Gson().fromJson(params)
    }
}