package com.gvelesiani.domain.useCases.screenshots

import com.gvelesiani.base.BaseUseCase
import com.gvelesiani.domain.repositories.ScreenshotsRepository

class GetTakingScreenshotsStatusUseCase(private val repository: ScreenshotsRepository) :
    BaseUseCase<Unit, Boolean>() {
    override suspend fun invoke(params: Unit): Boolean {
        return repository.getTakingScreenshotsStatus()
    }
}