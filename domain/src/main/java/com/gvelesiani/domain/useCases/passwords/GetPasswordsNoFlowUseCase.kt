package com.gvelesiani.domain.useCases.passwords

import com.gvelesiani.base.BaseUseCase
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.common.transformers.transformToModel
import com.gvelesiani.domain.repositories.PasswordsRepository

class GetPasswordsNoFlowUseCase(private val repository: PasswordsRepository) :
    BaseUseCase<Boolean, List<PasswordModel>>() {
    override suspend fun invoke(params: Boolean): List<PasswordModel> {
        return repository.getPasswordsNoFlow(params).map {
            it.transformToModel()
        }
    }
}