package com.gvelesiani.passworx.domain.useCases.intro

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.domain.repositories.IntroRepository

class FinishIntroUseCase(private val repository: IntroRepository) : BaseUseCase<Unit, Unit>() {
    override suspend fun invoke(params: Unit) {
        return repository.finishIntro()
    }
}