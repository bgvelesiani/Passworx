package com.gvelesiani.passworx.domain.useCases

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.data.models.PasswordModel
import com.gvelesiani.passworx.data.repository.Repository

class AddNewPasswordUseCase(private val repository: Repository) :
    BaseUseCase<PasswordModel, Unit>() {
    override suspend fun invoke(params: PasswordModel) {
        repository.addNewPassword(params)
    }
}