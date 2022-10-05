package com.gvelesiani.passworx.ui.masterPassword.changeMasterPassword

import androidx.lifecycle.ViewModel
import com.gvelesiani.domain.useCases.masterPassword.CreateOrChangeMasterPasswordUseCase
import com.gvelesiani.domain.useCases.masterPassword.GetMasterPasswordUseCase
import com.gvelesiani.helpers.helpers.hashPassword.PasswordHashHelper
import com.gvelesiani.helpers.helpers.validateMasterPassword.MasterPasswordValidatorHelper
import com.gvelesiani.helpers.helpers.validateMasterPassword.MasterPasswordValidatorHelperImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChangeMasterPasswordVM(
    private val createOrChangeMasterPassword: CreateOrChangeMasterPasswordUseCase,
    private val passwordHashHelper: PasswordHashHelper,
    private val getMasterPasswordUseCase: GetMasterPasswordUseCase,
    private val masterPasswordValidatorHelper: MasterPasswordValidatorHelper
) : ViewModel() {
    private val _uiState = MutableStateFlow<ChangePasswordUIState>(ChangePasswordUIState.Empty)
    val uiState: StateFlow<ChangePasswordUIState> = _uiState

    private val _validationErrors: MutableStateFlow<MutableList<MasterPasswordValidatorHelperImpl.MasterPasswordError>> =
        MutableStateFlow(
            mutableListOf()
        )
    val validationErrors: StateFlow<MutableList<MasterPasswordValidatorHelperImpl.MasterPasswordError>> =
        _validationErrors

    init {
        _validationErrors.value = masterPasswordValidatorHelper.getMasterPasswordErrors()
    }

    private fun changeMasterPassword(newMasterPassword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val masterPasswordModel = passwordHashHelper.hash(newMasterPassword)
            createOrChangeMasterPassword(masterPasswordModel)
            _uiState.value = ChangePasswordUIState.Success
        }
    }

    fun validate(
        masterPassword: String, newMasterPassword: String
    ) {
        _uiState.value = ChangePasswordUIState.Loading
        CoroutineScope(Dispatchers.IO).launch {
            val masterPass = getMasterPasswordUseCase.invoke(Unit)
            if (!passwordHashHelper.verify(masterPassword, masterPass)) {
                _uiState.value = ChangePasswordUIState.Error("Please enter correct master password")
            } else {
                _uiState.value = ChangePasswordUIState.Empty
            }

            val isValid = passwordHashHelper.verify(
                masterPassword,
                masterPass
            ) && newMasterPassword.isNotEmpty()
            if (isValid) {
                changeMasterPassword(newMasterPassword = newMasterPassword)
            }
        }
    }

    fun validateNewPassword(newPassword: String) {
        if (masterPasswordValidatorHelper.isValidPassword(newPassword))
            _uiState.value = ChangePasswordUIState.IsValid
    }
}

sealed class ChangePasswordUIState {
    object Success : ChangePasswordUIState()
    object Empty : ChangePasswordUIState()
    object Loading : ChangePasswordUIState()
    object IsValid : ChangePasswordUIState()
    data class Error(val errorMsg: String) : ChangePasswordUIState()
}