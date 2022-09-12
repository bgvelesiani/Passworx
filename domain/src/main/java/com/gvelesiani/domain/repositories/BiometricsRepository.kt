package com.gvelesiani.domain.repositories

interface BiometricsRepository {
    fun allowBiometrics(allow: Boolean)
    fun getBiometricsAllowingStatus(): Boolean
}