package com.gvelesiani.passworx.ui.masterPassword.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.domain.useCases.biometrics.GetBiometricsAllowingStatusUserCase
import com.gvelesiani.domain.useCases.masterPassword.GetMasterPasswordUseCase
import com.gvelesiani.helpers.helpers.biometrics.BiometricsHelper
import com.gvelesiani.helpers.helpers.hashPassword.PasswordHashHelper
import kotlinx.coroutines.launch

class MasterPasswordVM(
    private val getMasterPasswordUseCase: GetMasterPasswordUseCase,
    private val passwordHashHelper: PasswordHashHelper,
    private val biometricsHelper: BiometricsHelper,
    private val getBiometricsAllowingStatusUserCase: GetBiometricsAllowingStatusUserCase
) : ViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    init {
        viewState.value = ViewState()
        getBiometricsAllowingStatus()
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

    private fun getBiometricsAllowingStatus() {
        viewModelScope.launch {
            try {
                val result = getBiometricsAllowingStatusUserCase(Unit)
                viewState.postValue(currentViewState().copy(biometricsAreAllowed = result))
            } catch (e: Exception) {
            }
        }
    }

    data class ViewState(
        val passwordMatchError: String? = null,
        val passwordMatches: Boolean = false,
        val biometricsAreAllowed: Boolean = false
    )
}