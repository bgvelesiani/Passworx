package com.gvelesiani.domain.useCases.biometrics

import com.gvelesiani.domain.repositories.BiometricsRepository

class GetBiometricsAllowingStatusUserCase(private val repository: BiometricsRepository) :
    com.gvelesiani.base.BaseUseCase<Unit, Boolean>() {
    override suspend fun invoke(params: Unit): Boolean {
        return repository.getBiometricsAllowingStatus()
    }

}