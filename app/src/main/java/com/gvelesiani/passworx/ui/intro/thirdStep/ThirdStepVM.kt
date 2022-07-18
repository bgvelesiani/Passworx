package com.gvelesiani.passworx.ui.intro.thirdStep

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.passworx.domain.useCases.AllowBiometricsUseCase
import com.gvelesiani.passworx.domain.useCases.AllowTakingScreenshotsUseCase
import kotlinx.coroutines.launch

class ThirdStepVM(
    private val allowTakingScreenshotsUseCase: AllowTakingScreenshotsUseCase,
    private val allowBiometricsUseCase: AllowBiometricsUseCase
) : ViewModel() {

    fun allowTakingScreenshots(allow: Boolean) {
        viewModelScope.launch {
            try {
                allowTakingScreenshotsUseCase(allow)
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