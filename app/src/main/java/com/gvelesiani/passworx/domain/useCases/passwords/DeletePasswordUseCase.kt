package com.gvelesiani.passworx.domain.useCases.passwords

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.domain.repositories.local.PasswordsRepository

class DeletePasswordUseCase(private val repository: PasswordsRepository) :
    BaseUseCase<Int, Unit>() {
    override suspend fun invoke(params: Int) {
        repository.deletePassword(params)
    }
}