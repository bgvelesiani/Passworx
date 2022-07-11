package com.gvelesiani.passworx.domain.useCases

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.data.repository.Repository
import com.gvelesiani.passworx.domain.model.PasswordModel
import com.gvelesiani.passworx.domain.transformers.transformToModel

class GetFavoritePasswordsUseCase(private val repository: Repository) :
    BaseUseCase<Unit, List<PasswordModel>>() {
    override suspend fun invoke(params: Unit): List<PasswordModel> {
        return repository.getFavoritePasswords().map {
            it.transformToModel()
        }
    }
}