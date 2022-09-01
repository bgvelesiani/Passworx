package com.gvelesiani.passworx.ui.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.passworx.domain.useCases.intro.FinishIntroUseCase
import kotlinx.coroutines.launch

class IntroVM(private val finishIntroUseCase: FinishIntroUseCase) : ViewModel() {

    fun finishIntro() {
        viewModelScope.launch {
            finishIntroUseCase(Unit)
        }
    }
}