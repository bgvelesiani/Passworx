package com.gvelesiani.domain.useCases.passwords

import com.gvelesiani.base.BaseUseCase
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.common.transformers.transformToDto
import com.gvelesiani.domain.repositories.PasswordsRepository

class AddNewPasswordUseCase(private val repository: PasswordsRepository) :
    BaseUseCase<PasswordModel, Unit>() {
    override suspend fun invoke(params: PasswordModel) {
        repository.addNewPassword(params.transformToDto())
    }
}