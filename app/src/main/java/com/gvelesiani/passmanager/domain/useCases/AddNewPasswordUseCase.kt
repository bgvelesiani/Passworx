package com.gvelesiani.passmanager.domain.useCases

import com.gvelesiani.passmanager.base.BaseUseCase
import com.gvelesiani.passmanager.data.models.PasswordModel
import com.gvelesiani.passmanager.data.repository.Repository

class AddNewPasswordUseCase(private val repository: Repository): BaseUseCase<PasswordModel, Unit>() {
    override suspend fun run(params: PasswordModel) {
        repository.addNewPassword(params)
    }
}