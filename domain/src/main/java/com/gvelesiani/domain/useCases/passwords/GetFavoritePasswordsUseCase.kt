package com.gvelesiani.domain.useCases.passwords

import com.gvelesiani.base.BaseUseCase
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.common.transformers.transformToModel
import com.gvelesiani.domain.repositories.PasswordsRepository

class GetFavoritePasswordsUseCase(private val repository: PasswordsRepository) :
    BaseUseCase<Unit, List<PasswordModel>>() {
    override suspend fun invoke(params: Unit): List<PasswordModel> {
        return repository.getFavoritePasswords().map {
            it.transformToModel()
        }
    }
}