package com.gvelesiani.passworx.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.passworx.domain.useCases.AllowTakingScreenshotsUseCase
import com.gvelesiani.passworx.domain.useCases.GetTakingScreenshotsStatusUseCase
import kotlinx.coroutines.launch

class SettingsVM(
    private val allowTakingScreenshotsUseCase: AllowTakingScreenshotsUseCase,
    private val getTakingScreenshotsStatusUseCase: GetTakingScreenshotsStatusUseCase
) :
    ViewModel() {

    val takingScreenshotsAreAllowed: MutableLiveData<Boolean> = MutableLiveData()

    init {
        getTakingScreenshotsStatus()
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
}