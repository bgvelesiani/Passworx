package com.gvelesiani.helpers.helpers.biometrics

import android.content.Context
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.gvelesiani.helpers.R
import com.gvelesiani.helpers.helpers.resourceProvider.ResourceHelper
import java.util.concurrent.Executor

class BiometricsHelperImpl(
    private val resourceHelper: ResourceHelper
) : BiometricsHelper {
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt

    override fun authenticate(subTitle: String, negativeButtonText: String) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(
                resourceHelper.getString(R.string.biometric_auth_title)
            )
            .setSubtitle(subTitle)
            .setNegativeButtonText(negativeButtonText)
            .build()
        biometricPrompt.authenticate(promptInfo)
    }

    override fun setupBiometricPrompt(
        fragment: FragmentActivity,
        context: Context,
        success: (Boolean) -> Unit
    ) {
        executor = ContextCompat.getMainExecutor(context)
        biometricPrompt = BiometricPrompt(fragment, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    success.invoke(true)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    success.invoke(false)
                }
            })
    }
}