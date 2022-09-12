package com.gvelesiani.passworx.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.domain.useCases.biometrics.AllowBiometricsUseCase
import com.gvelesiani.domain.useCases.biometrics.GetBiometricsAllowingStatusUserCase
import com.gvelesiani.domain.useCases.screenshots.GetTakingScreenshotsStatusUseCase
import com.gvelesiani.domain.useCases.screenshots.PreventTakingScreenshotsUseCase
import kotlinx.coroutines.launch

class SettingsVM(
    private val preventTakingScreenshotsUseCase: PreventTakingScreenshotsUseCase,
    private val getTakingScreenshotsStatusUseCase: GetTakingScreenshotsStatusUseCase,
    private val allowBiometricsUseCase: AllowBiometricsUseCase,
    private val getBiometricsAllowingStatusUserCase: GetBiometricsAllowingStatusUserCase
) :
    ViewModel() {

    val takingScreenshotsArePrevented: MutableLiveData<Boolean> = MutableLiveData()
    val biometricsAreAllowed: MutableLiveData<Boolean> = MutableLiveData()

    init {
        getTakingScreenshotsStatus()
        getBiometricsAllowingStatus()
    }

    private fun getTakingScreenshotsStatus() {
        viewModelScope.launch {
            try {
                val result = getTakingScreenshotsStatusUseCase(Unit)
                takingScreenshotsArePrevented.postValue(result)
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
                biometricsAreAllowed.postValue(result)
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