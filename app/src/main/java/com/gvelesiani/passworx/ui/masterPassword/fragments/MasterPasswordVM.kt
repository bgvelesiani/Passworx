package com.gvelesiani.passworx.ui.masterPassword.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.passworx.domain.useCases.GetMasterPasswordUseCase
import com.gvelesiani.passworx.helpers.biometrics.BiometricsHelper
import com.gvelesiani.passworx.helpers.hashPassword.PasswordHashHelper
import kotlinx.coroutines.launch

class MasterPasswordVM(
    private val getMasterPasswordUseCase: GetMasterPasswordUseCase,
    private val passwordHashHelper: PasswordHashHelper,
    private val biometricsHelper: BiometricsHelper
) : ViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    init {
        viewState.value = ViewState()
    }

    private fun currentViewState(): ViewState = viewState.value!!

    fun doesPasswordMatch(password: String) {
        viewModelScope.launch {
            try {
                val result = getMasterPasswordUseCase.invoke(Unit)
                if (!passwordHashHelper.verify(password, result)) {
                    viewState.postValue(currentViewState().copy(passwordMatchError = "Master password is incorrect, please try again"))
                } else {
                    viewState.postValue(
                        currentViewState().copy(
                            passwordMatches = true,
                            passwordMatchError = null
                        )
                    )
                }
            } catch (ignored: Exception) {
            }
        }
    }

    fun getBiometrics(): BiometricsHelper = biometricsHelper

    data class ViewState(
        val passwordMatchError: String? = null,
        val passwordMatches: Boolean = false
    )
}