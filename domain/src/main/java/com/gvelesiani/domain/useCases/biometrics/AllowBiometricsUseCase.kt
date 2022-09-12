package com.gvelesiani.domain.useCases.biometrics

import com.gvelesiani.domain.repositories.BiometricsRepository

class AllowBiometricsUseCase(private val repository: BiometricsRepository) :
    com.gvelesiani.base.BaseUseCase<Boolean, Unit>() {
    override suspend fun invoke(params: Boolean) {
        repository.allowBiometrics(params)
    }

}