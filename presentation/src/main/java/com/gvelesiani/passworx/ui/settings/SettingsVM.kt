package com.gvelesiani.passworx.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.domain.useCases.biometrics.AllowBiometricsUseCase
import com.gvelesiani.domain.useCases.biometrics.GetBiometricsAllowingStatusUserCase
import com.gvelesiani.domain.useCases.screenshots.GetTakingScreenshotsStatusUseCase
import com.gvelesiani.domain.useCases.screenshots.PreventTakingScreenshotsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SettingsVM(
    private val preventTakingScreenshotsUseCase: PreventTakingScreenshotsUseCase,
    private val getTakingScreenshotsStatusUseCase: GetTakingScreenshotsStatusUseCase,
    private val allowBiometricsUseCase: AllowBiometricsUseCase,
    private val getBiometricsAllowingStatusUserCase: GetBiometricsAllowingStatusUserCase
) :
    ViewModel() {
    val takingScreenshotsArePrevented: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val biometricsAreAllowed: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        getTakingScreenshotsStatus()
        getBiometricsAllowingStatus()
    }

    private fun getTakingScreenshotsStatus() {
        viewModelScope.launch {
            try {
                val result = getTakingScreenshotsStatusUseCase(Unit)
                takingScreenshotsArePrevented.value = result
            } catch (ignored: Exception) {
            }
        }
    }

    fun allowTakingScreenshots(prevent: Boolean) {
        viewModelScope.launch {
            try {
                preventTakingScreenshotsUseCase(prevent)
                getTakingScreenshotsStatus()
            } catch (e: Exception) {
            }
        }
    }

    private fun getBiometricsAllowingStatus() {
        viewModelScope.launch {
            try {
                val result = getBiometricsAllowingStatusUserCase(Unit)
                biometricsAreAllowed.value = result
            } catch (e: Exception) {
            }
        }
    }

    fun allowBiometrics(allow: Boolean) {
        viewModelScope.launch {
            try {
                allowBiometricsUseCase(allow)
                getBiometricsAllowingStatus()
            } catch (e: Exception) {
            }
        }
    }
}