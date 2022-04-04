package com.gvelesiani.passworx.ui.masterPassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gvelesiani.passworx.base.BaseViewModel
import com.gvelesiani.passworx.domain.useCases.GetMasterPasswordUseCase
import com.gvelesiani.passworx.helpers.hashPassword.PasswordHashHelper
import kotlinx.coroutines.launch

class MasterPasswordVM(
    private val getMasterPasswordUseCase: GetMasterPasswordUseCase,
    private val passwordHashHelper: PasswordHashHelper
) : BaseViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    init {
        viewState.value = ViewState()
    }

    private fun currentViewState(): ViewState = viewState.value!!

    fun doesPasswordMatch(password: String) {
        viewModelScope.launch {
            try {
                val result = getMasterPasswordUseCase.run(Unit)
                if (!passwordHashHelper.verify(password, result)) {
                    viewState.postValue(currentViewState().copy(passwordMatchError = "Master password is incorrect, please try again"))
                } else {
                    viewState.postValue(currentViewState().copy(passwordMatches = true))
                }
            } catch (ignored: Exception) {
            }
        }
    }

    data class ViewState(
        val passwordMatchError: String? = null,
        val passwordMatches: Boolean = false
    )
}