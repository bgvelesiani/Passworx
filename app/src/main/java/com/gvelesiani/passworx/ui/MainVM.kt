package com.gvelesiani.passworx.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.passworx.domain.useCases.GetTakingScreenshotsStatusUseCase
import kotlinx.coroutines.launch

class MainVM(private val getTakingScreenshotsStatusUseCase: GetTakingScreenshotsStatusUseCase) :
    ViewModel() {
    val takingScreenshotsArePrevented: MutableLiveData<Boolean> = MutableLiveData()

    init {
        getTakingScreenshotsStatus()
    }

    private fun getTakingScreenshotsStatus() {
        viewModelScope.launch {
            try {
                val result = getTakingScreenshotsStatusUseCase.invoke(Unit)
                takingScreenshotsArePrevented.postValue(result)
            } catch (ignored: Exception) {
            }
        }
    }
}