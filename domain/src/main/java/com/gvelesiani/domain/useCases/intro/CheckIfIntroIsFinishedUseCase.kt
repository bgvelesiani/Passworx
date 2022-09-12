package com.gvelesiani.domain.useCases.intro

import com.gvelesiani.domain.repositories.IntroRepository

class CheckIfIntroIsFinishedUseCase(private val repository: IntroRepository) :
    com.gvelesiani.base.BaseUseCase<Unit, Boolean>() {
    override suspend fun invoke(params: Unit): Boolean {
        return repository.isIntroFinished()
    }
}