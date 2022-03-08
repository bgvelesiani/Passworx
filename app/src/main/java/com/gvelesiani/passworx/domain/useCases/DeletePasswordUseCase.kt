package com.gvelesiani.passworx.domain.useCases

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.data.repository.Repository

class DeletePasswordUseCase(private val repository: Repository) : BaseUseCase<Int, Unit>() {
    override suspend fun run(params: Int) {
        repository.deletePassword(params)
    }
}