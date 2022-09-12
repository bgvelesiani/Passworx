package com.gvelesiani.domain.useCases.screenshots

import com.gvelesiani.domain.repositories.ScreenshotsRepository

class GetTakingScreenshotsStatusUseCase(private val repository: ScreenshotsRepository) :
    com.gvelesiani.base.BaseUseCase<Unit, Boolean>() {
    override suspend fun invoke(params: Unit): Boolean {
        return repository.getTakingScreenshotsStatus()
    }
}