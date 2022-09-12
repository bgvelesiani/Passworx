package com.gvelesiani.domain.useCases.intro

import com.gvelesiani.domain.repositories.IntroRepository

class FinishIntroUseCase(private val repository: IntroRepository) : com.gvelesiani.base.BaseUseCase<Unit, Unit>() {
    override suspend fun invoke(params: Unit) {
        return repository.finishIntro()
    }
}