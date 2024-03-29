package com.gvelesiani.domain.useCases.passwords

import com.gvelesiani.base.BaseUseCase
import com.gvelesiani.domain.repositories.PasswordsRepository

class UpdateFavoriteStateUseCase(private val repository: PasswordsRepository) :
    BaseUseCase<Pair<Boolean, Int>, Unit>() {
    override suspend fun invoke(params: Pair<Boolean, Int>) {
        repository.updateFavoriteState(params.first, params.second)
    }
}