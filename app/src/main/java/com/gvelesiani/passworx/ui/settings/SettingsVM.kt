package com.gvelesiani.passworx.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.passworx.domain.useCases.AllowBiometricsUseCase
import com.gvelesiani.passworx.domain.useCases.AllowTakingScreenshotsUseCase
import com.gvelesiani.passworx.domain.useCases.GetBiometricsAllowingStatusUserCase
import com.gvelesiani.passworx.domain.useCases.GetTakingScreenshotsStatusUseCase
import kotlinx.coroutines.launch

class SettingsVM(
    private val allowTakingScreenshotsUseCase: AllowTakingScreenshotsUseCase,
    private val getTakingScreenshotsStatusUseCase: GetTakingScreenshotsStatusUseCase,
    private val allowBiometricsUseCase: AllowBiometricsUseCase,
    private val getBiometricsAllowingStatusUserCase: GetBiometricsAllowingStatusUserCase
) :
    ViewModel() {

    val takingScreenshotsAreAllowed: MutableLiveData<Boolean> = MutableLiveData()
    val biometricsAreAllowed: MutableLiveData<Boolean> = MutableLiveData()

    init {
        getTakingScreenshotsStatus()
        getBiometricsAllowingStatus()
    }

    private fun getTakingScreenshotsStatus() {
        viewModelScope.launch {
            try {
                val result = getTakingScreenshotsStatusUseCase(Unit)
                takingScreenshotsAreAllowed.postValue(result)
            } catch (ignored: Exception) {
            }
        }
    }

    fun allowTakingScreenshots(allow: Boolean) {
        viewModelScope.launch {
            try {
                allowTakingScreenshotsUseCase(allow)
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