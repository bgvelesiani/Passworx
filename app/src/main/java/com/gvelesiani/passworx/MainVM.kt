package com.gvelesiani.passworx

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gvelesiani.passworx.domain.useCases.GetMasterPasswordUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainVM(private val getMasterPasswordUseCase: GetMasterPasswordUseCase) : ViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    init {
        viewState.value = ViewState()
    }

    private fun currentViewState(): ViewState = viewState.value!!

    fun getMasterPassword() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = getMasterPasswordUseCase.run(Unit)
                viewState.postValue(currentViewState().copy(masterPassword = result))
            } catch (ignored: Exception) {
            }
        }
    }

    data class ViewState(
        val masterPassword: String = ""
    )
}