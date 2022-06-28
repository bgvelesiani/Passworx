package com.gvelesiani.passworx.domain.useCases

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.data.repository.Repository

class UpdateFavoriteStateUseCase(private val repository: Repository) :
    BaseUseCase<Pair<Boolean, Int>, Unit>() {
    override suspend operator fun invoke(params: Pair<Boolean, Int>) {
        repository.updateFavoriteState(params.first, params.second)
    }
}