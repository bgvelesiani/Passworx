package com.gvelesiani.passworx.uiCompose.masterPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.domain.useCases.biometrics.GetBiometricsAllowingStatusUserCase
import com.gvelesiani.domain.useCases.masterPassword.GetMasterPasswordUseCase
import com.gvelesiani.helpers.helpers.biometrics.BiometricsHelper
import com.gvelesiani.helpers.helpers.hashPassword.PasswordHashHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MasterPasswordVM(
    private val getMasterPasswordUseCase: GetMasterPasswordUseCase,
    private val passwordHashHelper: PasswordHashHelper,
    private val biometricsHelper: BiometricsHelper,
    private val getBiometricsAllowingStatusUserCase: GetBiometricsAllowingStatusUserCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<MasterPasswordUiState>(MasterPasswordUiState.Empty)
    val uiState: StateFlow<MasterPasswordUiState> = _uiState

    init {
        getBiometricsAllowingStatus()
    }

    fun doesPasswordMatch(password: String) {
        _uiState.value = MasterPasswordUiState.Loading
        viewModelScope.launch {
            val result = getMasterPasswordUseCase.invoke(Unit)
            if (!passwordHashHelper.verify(password, result)) {
                _uiState.value =
                    MasterPasswordUiState.Error("Master password is incorrect, please try again")
            } else {
                _uiState.value = MasterPasswordUiState.PasswordMatchSuccess
            }
        }
    }

    fun getBiometrics(): BiometricsHelper = biometricsHelper

    private fun getBiometricsAllowingStatus() {
        viewModelScope.launch {
            val result = getBiometricsAllowingStatusUserCase(Unit)
            _uiState.value = MasterPasswordUiState.BiometricsAreAllowed(result)
        }
    }
}

sealed class MasterPasswordUiState {
    object Empty : MasterPasswordUiState()
    object Loading : MasterPasswordUiState()
    data class Error(val errorMsg: String) : MasterPasswordUiState()
    data class BiometricsAreAllowed(val allowed: Boolean) : MasterPasswordUiState()
    object PasswordMatchSuccess : MasterPasswordUiState()
}