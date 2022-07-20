package com.gvelesiani.passworx.ui.intro.thirdStep

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.passworx.domain.useCases.AllowBiometricsUseCase
import com.gvelesiani.passworx.domain.useCases.PreventTakingScreenshotsUseCase
import kotlinx.coroutines.launch

class ThirdStepVM(
    private val preventTakingScreenshotsUseCase: PreventTakingScreenshotsUseCase,
    private val allowBiometricsUseCase: AllowBiometricsUseCase
) : ViewModel() {

    fun allowTakingScreenshots(prevent: Boolean) {
        viewModelScope.launch {
            try {
                preventTakingScreenshotsUseCase(prevent)
            } catch (e: Exception) {
            }
        }
    }

    fun allowBiometrics(allow: Boolean) {
        viewModelScope.launch {
            try {
                allowBiometricsUseCase(allow)
            } catch (e: Exception) {
            }
        }
    }
}