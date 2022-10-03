package com.gvelesiani.passworx.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.domain.useCases.intro.CheckIfIntroIsFinishedUseCase
import com.gvelesiani.domain.useCases.masterPassword.GetMasterPasswordUseCase
import com.gvelesiani.domain.useCases.screenshots.GetTakingScreenshotsStatusUseCase
import com.gvelesiani.passworx.navGraph.StartScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainVM(
    private val getTakingScreenshotsStatusUseCase: GetTakingScreenshotsStatusUseCase,
    private val introIsFinishedUseCase: CheckIfIntroIsFinishedUseCase,
    private val getMasterPasswordUseCase: GetMasterPasswordUseCase,
) :
    ViewModel() {
    val takingScreenshotsArePrevented: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val startingScreenState: MutableStateFlow<StartScreen> = MutableStateFlow(StartScreen.None)

    init {
        checkStatuses()
        getTakingScreenshotsStatus()
    }

    private fun getTakingScreenshotsStatus() {
        viewModelScope.launch {
            val result = getTakingScreenshotsStatusUseCase.invoke(Unit)
            takingScreenshotsArePrevented.value = result
        }
    }

    private fun checkStatuses() {
        viewModelScope.launch {
            val introFinished = introIsFinishedUseCase.invoke(Unit)
            val masterPassword = getMasterPasswordUseCase.invoke(Unit)

            startingScreenState.value =
                if (!introFinished) {
                    StartScreen.Intro
                } else {
                    if (masterPassword != "") {
                        StartScreen.Master
                    } else {
                        StartScreen.Create
                    }
                }
        }
    }
}