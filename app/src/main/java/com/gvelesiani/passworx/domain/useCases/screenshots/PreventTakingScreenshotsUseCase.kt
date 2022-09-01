package com.gvelesiani.passworx.domain.useCases.screenshots

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.domain.repositories.local.ScreenshotsRepository

class PreventTakingScreenshotsUseCase(private val repository: ScreenshotsRepository) :
    BaseUseCase<Boolean, Unit>() {
    override suspend fun invoke(params: Boolean) {
        repository.preventTakingScreenshots(params)
    }
}