package com.gvelesiani.passworx.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.domain.useCases.screenshots.GetTakingScreenshotsStatusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainVM(
    private val getTakingScreenshotsStatusUseCase: GetTakingScreenshotsStatusUseCase
) :
    ViewModel() {
    val takingScreenshotsArePrevented: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        getTakingScreenshotsStatus()
    }

    private fun getTakingScreenshotsStatus() {
        viewModelScope.launch {
            try {
                val result = getTakingScreenshotsStatusUseCase.invoke(Unit)
                takingScreenshotsArePrevented.value = result
            } catch (ignored: Exception) {
            }
        }
    }
}