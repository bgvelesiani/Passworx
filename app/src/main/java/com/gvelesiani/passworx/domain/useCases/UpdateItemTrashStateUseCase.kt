package com.gvelesiani.passworx.domain.useCases

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.data.repository.Repository

class UpdateItemTrashStateUseCase(private val repository: Repository) :
    BaseUseCase<Pair<Boolean, Int>, Unit>() {
    override suspend fun run(params: Pair<Boolean, Int>) {
        repository.updateItemTrashState(params.first, params.second)
    }
}