package com.gvelesiani.passworx.domain.useCases.passwords

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.domain.repositories.local.PasswordsRepository

class UpdateItemTrashStateUseCase(private val repository: PasswordsRepository) :
    BaseUseCase<Pair<Boolean, Int>, Unit>() {
    override suspend fun invoke(params: Pair<Boolean, Int>) {
        repository.updateItemTrashState(params.first, params.second)
    }

}