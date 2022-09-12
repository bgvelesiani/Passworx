package com.gvelesiani.data.repositories.local

import android.content.SharedPreferences
import com.gvelesiani.data.common.BIOMETRICS_ALLOWED
import com.gvelesiani.domain.repositories.BiometricsRepository

class BiometricsRepositoryImpl constructor(
    private val preferences: SharedPreferences
) : BiometricsRepository {
    override fun allowBiometrics(allow: Boolean) {
        preferences.edit().putBoolean(BIOMETRICS_ALLOWED, allow).apply()
    }

    override fun getBiometricsAllowingStatus(): Boolean {
        return preferences.getBoolean(BIOMETRICS_ALLOWED, false)
    }
}