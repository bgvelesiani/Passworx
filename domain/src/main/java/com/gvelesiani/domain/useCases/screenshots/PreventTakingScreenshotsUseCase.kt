package com.gvelesiani.domain.useCases.screenshots

import com.gvelesiani.base.BaseUseCase
import com.gvelesiani.domain.repositories.ScreenshotsRepository

class PreventTakingScreenshotsUseCase(private val repository: ScreenshotsRepository) :
    BaseUseCase<Boolean, Unit>() {
    override suspend fun invoke(params: Boolean) {
        repository.preventTakingScreenshots(params)
    }
}