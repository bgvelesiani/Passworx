package com.gvelesiani.passworx.ui.masterPassword.fragments

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
    private val _uiState = MutableStateFlow<MasterPasswordUiState>(MasterPasswordUiState.EmptyState)
    val uiState: StateFlow<MasterPasswordUiState> = _uiState

    init {
        getBiometricsAllowingStatus()
    }

    fun doesPasswordMatch(password: String) {
        _uiState.value = MasterPasswordUiState.Loading
        viewModelScope.launch {
            try {
                val result = getMasterPasswordUseCase.invoke(Unit)
                if (!passwordHashHelper.verify(password, result)) {
                    _uiState.value = MasterPasswordUiState.PasswordMatchError
                } else {
                    _uiState.value = MasterPasswordUiState.PasswordMatchSuccess
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
                _uiState.value = MasterPasswordUiState.BiometricsAreAllowed(allowed = result)
            } catch (e: Exception) {
            }
        }
    }
}

sealed class MasterPasswordUiState {
    object PasswordMatchSuccess : MasterPasswordUiState()
    object PasswordMatchError : MasterPasswordUiState()
    object Loading : MasterPasswordUiState()
    object EmptyState : MasterPasswordUiState()
    data class BiometricsAreAllowed(val allowed: Boolean) : MasterPasswordUiState()
}