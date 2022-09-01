package com.gvelesiani.passworx.domain.useCases.intro

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.domain.repositories.IntroRepository

class CheckIfIntroIsFinishedUseCase(private val repository: IntroRepository) :
    BaseUseCase<Unit, Boolean>() {
    override suspend fun invoke(params: Unit): Boolean {
        return repository.isIntroFinished()
    }
}