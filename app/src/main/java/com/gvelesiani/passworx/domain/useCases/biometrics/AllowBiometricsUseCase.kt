package com.gvelesiani.passworx.domain.useCases.biometrics

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.domain.repositories.local.BiometricsRepository

class AllowBiometricsUseCase(private val repository: BiometricsRepository) :
    BaseUseCase<Boolean, Unit>() {
    override suspend fun invoke(params: Boolean) {
        repository.allowBiometrics(params)
    }

}