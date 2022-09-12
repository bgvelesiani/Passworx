package com.gvelesiani.domain.useCases.passwords

import com.gvelesiani.domain.model.PasswordModel
import com.gvelesiani.domain.repositories.PasswordsRepository

class GetPasswordsUseCase(private val repository: PasswordsRepository) :
    com.gvelesiani.base.BaseUseCase<Boolean, List<PasswordModel>>() {
    override suspend fun invoke(params: Boolean): List<PasswordModel> {
        return repository.getPasswords(params)
    }
}