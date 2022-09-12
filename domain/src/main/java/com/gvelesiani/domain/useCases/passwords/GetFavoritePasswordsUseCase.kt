package com.gvelesiani.domain.useCases.passwords

import com.gvelesiani.domain.model.PasswordModel
import com.gvelesiani.domain.repositories.PasswordsRepository

class GetFavoritePasswordsUseCase(private val repository: PasswordsRepository) :
    com.gvelesiani.base.BaseUseCase<Unit, List<PasswordModel>>() {
    override suspend fun invoke(params: Unit): List<PasswordModel> {
        return repository.getFavoritePasswords()
    }
}