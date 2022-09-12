package com.gvelesiani.domain.useCases.passwords

import com.gvelesiani.domain.repositories.PasswordsRepository

class DeletePasswordUseCase(private val repository: PasswordsRepository) :
    com.gvelesiani.base.BaseUseCase<Int, Unit>() {
    override suspend fun invoke(params: Int) {
        repository.deletePassword(params)
    }
}