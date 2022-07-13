package com.gvelesiani.passworx.domain.useCases

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.data.repository.Repository

class FinishIntroUseCase(private val repository: Repository) : BaseUseCase<Unit, Unit>() {
    override suspend fun invoke(params: Unit) {
        return repository.finishIntro()
    }
}