package com.gvelesiani.passmanager.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel

abstract class BaseAndroidVM(application: Application) : AndroidViewModel(application) {
    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}