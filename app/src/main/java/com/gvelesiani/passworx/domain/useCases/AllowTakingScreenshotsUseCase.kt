package com.gvelesiani.passworx.domain.useCases

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.data.repository.Repository

class AllowTakingScreenshotsUseCase(private val repository: Repository) :
    BaseUseCase<Boolean, Unit>() {
    override suspend fun invoke(params: Boolean) {
        repository.allowTakingScreenshots(params)
    }
}