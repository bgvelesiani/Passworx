package com.gvelesiani.domain.useCases.passwords

import com.gvelesiani.domain.model.PasswordModel
import com.gvelesiani.domain.repositories.PasswordsRepository

class AddNewPasswordUseCase(private val repository: PasswordsRepository) :
    com.gvelesiani.base.BaseUseCase<PasswordModel, Unit>() {
    override suspend fun invoke(params: PasswordModel) {
        repository.addNewPassword(params)
    }
}