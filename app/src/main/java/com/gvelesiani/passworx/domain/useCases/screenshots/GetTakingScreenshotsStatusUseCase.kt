package com.gvelesiani.passworx.domain.useCases.screenshots

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.domain.repositories.local.ScreenshotsRepository

class GetTakingScreenshotsStatusUseCase(private val repository: ScreenshotsRepository) :
    BaseUseCase<Unit, Boolean>() {
    override suspend fun invoke(params: Unit): Boolean {
        return repository.getTakingScreenshotsStatus()
    }
}