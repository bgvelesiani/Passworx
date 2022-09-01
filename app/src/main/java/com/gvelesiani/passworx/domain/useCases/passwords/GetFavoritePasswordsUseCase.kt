package com.gvelesiani.passworx.domain.useCases.passwords

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.data.transformers.transformToModel
import com.gvelesiani.passworx.domain.model.PasswordModel
import com.gvelesiani.passworx.domain.repositories.local.PasswordsRepository

class GetFavoritePasswordsUseCase(private val repository: PasswordsRepository) :
    BaseUseCase<Unit, List<PasswordModel>>() {
    override suspend fun invoke(params: Unit): List<PasswordModel> {
        return repository.getFavoritePasswords().map {
            it.transformToModel()
        }
    }
}