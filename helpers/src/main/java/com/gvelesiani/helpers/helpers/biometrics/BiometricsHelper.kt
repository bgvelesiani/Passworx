package com.gvelesiani.helpers.helpers.biometrics

import android.content.Context
import androidx.fragment.app.FragmentActivity

interface BiometricsHelper {
    fun authenticate(subTitle: String, negativeButtonText: String)
    fun setupBiometricPrompt(
        fragment: FragmentActivity,
        context: Context,
        success: (Boolean) -> Unit
    )
}