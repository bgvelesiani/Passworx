package com.gvelesiani.passworx.domain.repositories.local

interface BiometricsRepository {
    fun allowBiometrics(allow: Boolean)
    fun getBiometricsAllowingStatus(): Boolean
}