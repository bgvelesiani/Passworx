package com.gvelesiani.passworx.domain.useCases.passwords

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.data.transformers.transformToModel
import com.gvelesiani.passworx.domain.model.PasswordModel
import com.gvelesiani.passworx.domain.repositories.local.PasswordsRepository

class GetPasswordsUseCase(private val repository: PasswordsRepository) :
    BaseUseCase<Boolean, List<PasswordModel>>() {
    override suspend fun invoke(params: Boolean): List<PasswordModel> {
        return repository.getPasswords(params).map {
            it.transformToModel()
        }
    }
}