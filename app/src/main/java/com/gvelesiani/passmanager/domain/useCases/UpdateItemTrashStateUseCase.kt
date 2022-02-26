package com.gvelesiani.passmanager.domain.useCases

import com.gvelesiani.passmanager.base.BaseUseCase
import com.gvelesiani.passmanager.data.repository.Repository

class UpdateItemTrashStateUseCase(private val repository: Repository): BaseUseCase<Pair<Boolean, Int>, Unit>() {
    override suspend fun run(params: Pair<Boolean, Int>) {
        repository.updateItemTrashState(params.first, params.second)
    }
}