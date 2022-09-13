package com.gvelesiani.domain.useCases.passwords

import com.gvelesiani.base.BaseUseCase
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.common.transformers.transformToModel
import com.gvelesiani.domain.repositories.PasswordsRepository

class GetPasswordsUseCase(private val repository: PasswordsRepository) :
    BaseUseCase<Boolean, List<PasswordModel>>() {
    override suspend fun invoke(params: Boolean): List<PasswordModel> {
        return repository.getPasswords(params).map {
            it.transformToModel()
        }
    }
}