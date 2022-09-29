package com.gvelesiani.passworx.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.domain.useCases.intro.CheckIfIntroIsFinishedUseCase
import com.gvelesiani.domain.useCases.screenshots.GetTakingScreenshotsStatusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainVM(
    private val getTakingScreenshotsStatusUseCase: GetTakingScreenshotsStatusUseCase,
    private val introIsFinishedUseCase: CheckIfIntroIsFinishedUseCase
) :
    ViewModel() {
    val takingScreenshotsArePrevented: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isIntroIsFinished: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        getIntroFinishedStatus()
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

    private fun getIntroFinishedStatus() {
        viewModelScope.launch {
            try {
                val result = introIsFinishedUseCase.invoke(Unit)
                isIntroIsFinished.value = result
            } catch (ignored: Exception) {
            }
        }
    }
}