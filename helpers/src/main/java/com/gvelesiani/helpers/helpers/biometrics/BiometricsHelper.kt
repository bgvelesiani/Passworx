package com.gvelesiani.helpers.helpers.biometrics

import android.content.Context
import androidx.fragment.app.Fragment

interface BiometricsHelper {
    fun authenticate()
    fun setupBiometricPrompt(
        fragment: Fragment,
        context: Context,
        success: (Boolean) -> Unit
    )
}