package com.gvelesiani.passworx.domain.useCases

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.data.repository.Repository

class AllowBiometricsUseCase(private val repository: Repository) :
    BaseUseCase<Boolean, Unit>() {
    override suspend fun invoke(params: Boolean) {
        repository.allowBiometrics(params)
    }

}