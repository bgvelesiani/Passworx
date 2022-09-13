package com.gvelesiani.domain.useCases.biometrics

import com.gvelesiani.base.BaseUseCase
import com.gvelesiani.domain.repositories.BiometricsRepository

class GetBiometricsAllowingStatusUserCase(private val repository: BiometricsRepository) :
    BaseUseCase<Unit, Boolean>() {
    override suspend fun invoke(params: Unit): Boolean {
        return repository.getBiometricsAllowingStatus()
    }

}