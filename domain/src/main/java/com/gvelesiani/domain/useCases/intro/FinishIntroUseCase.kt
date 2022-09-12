package com.gvelesiani.domain.useCases.intro

import com.gvelesiani.base.BaseUseCase
import com.gvelesiani.domain.repositories.IntroRepository

class FinishIntroUseCase(private val repository: IntroRepository) : BaseUseCase<Unit, Unit>() {
    override suspend fun invoke(params: Unit) {
        return repository.finishIntro()
    }
}