package com.gvelesiani.domain.useCases.biometrics

import com.gvelesiani.base.BaseUseCase
import com.gvelesiani.domain.repositories.BiometricsRepository

class AllowBiometricsUseCase(private val repository: BiometricsRepository) :
    BaseUseCase<Boolean, Unit>() {
    override suspend fun invoke(params: Boolean) {
        repository.allowBiometrics(params)
    }
}