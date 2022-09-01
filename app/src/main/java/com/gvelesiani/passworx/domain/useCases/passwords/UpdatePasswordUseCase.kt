package com.gvelesiani.passworx.domain.useCases.passwords

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.data.transformers.transformToDto
import com.gvelesiani.passworx.domain.model.PasswordModel
import com.gvelesiani.passworx.domain.repositories.local.PasswordsRepository

class UpdatePasswordUseCase(private val repository: PasswordsRepository) :
    BaseUseCase<PasswordModel, Unit>() {
    override suspend fun invoke(params: PasswordModel) {
        repository.updatePassword(params.transformToDto())
    }
}