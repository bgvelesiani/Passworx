package com.gvelesiani.domain.useCases.passwords

import com.gvelesiani.domain.repositories.PasswordsRepository

class UpdateItemTrashStateUseCase(private val repository: PasswordsRepository) :
    com.gvelesiani.base.BaseUseCase<Pair<Boolean, Int>, Unit>() {
    override suspend fun invoke(params: Pair<Boolean, Int>) {
        repository.updateItemTrashState(params.first, params.second)
    }

}