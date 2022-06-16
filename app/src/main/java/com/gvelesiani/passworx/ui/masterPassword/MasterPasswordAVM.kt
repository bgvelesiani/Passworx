package com.gvelesiani.passworx.ui.masterPassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.passworx.domain.useCases.GetMasterPasswordUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MasterPasswordAVM(private val getMasterPasswordUseCase: GetMasterPasswordUseCase) : ViewModel() {
    val masterPassword: MutableLiveData<String> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getMasterPassword() {
        isLoading.value = true
        viewModelScope.launch {
            try {
                delay(1000)
                isLoading.postValue(false)
                val result = getMasterPasswordUseCase.run(Unit)
                masterPassword.value = result
            } catch (e: Exception) {
                isLoading.postValue(false)
            }
        }
    }
}