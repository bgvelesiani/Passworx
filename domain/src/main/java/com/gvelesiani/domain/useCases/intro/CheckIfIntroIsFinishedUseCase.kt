package com.gvelesiani.domain.useCases.intro

import com.gvelesiani.base.BaseUseCase
import com.gvelesiani.domain.repositories.IntroRepository

class CheckIfIntroIsFinishedUseCase(private val repository: IntroRepository) :
    BaseUseCase<Unit, Boolean>() {
    override suspend fun invoke(params: Unit): Boolean {
        return repository.isIntroFinished()
    }
}