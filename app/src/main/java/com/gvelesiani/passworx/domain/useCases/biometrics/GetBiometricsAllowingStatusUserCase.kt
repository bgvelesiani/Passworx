package com.gvelesiani.passworx.domain.useCases.biometrics

import com.gvelesiani.passworx.base.BaseUseCase
import com.gvelesiani.passworx.domain.repositories.local.BiometricsRepository

class GetBiometricsAllowingStatusUserCase(private val repository: BiometricsRepository) :
    BaseUseCase<Unit, Boolean>() {
    override suspend fun invoke(params: Unit): Boolean {
        return repository.getBiometricsAllowingStatus()
    }

}