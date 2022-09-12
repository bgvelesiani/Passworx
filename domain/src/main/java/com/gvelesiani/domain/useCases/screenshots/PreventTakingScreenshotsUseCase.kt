package com.gvelesiani.domain.useCases.screenshots

import com.gvelesiani.domain.repositories.ScreenshotsRepository

class PreventTakingScreenshotsUseCase(private val repository: ScreenshotsRepository) :
    com.gvelesiani.base.BaseUseCase<Boolean, Unit>() {
    override suspend fun invoke(params: Boolean) {
        repository.preventTakingScreenshots(params)
    }
}