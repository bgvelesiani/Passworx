package com.gvelesiani.passmanager.domain.useCases

import com.gvelesiani.passmanager.base.BaseUseCase
import com.gvelesiani.passmanager.data.repository.Repository

class DeletePasswordUseCase(private val repository: Repository): BaseUseCase<Int, Unit>() {
    override suspend fun run(params: Int) {
        repository.deletePassword(params)
    }
}