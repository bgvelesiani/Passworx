package com.gvelesiani.passworx.ui.backupAndRestore

import androidx.lifecycle.ViewModel
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.domain.useCases.backup.GetPasswordsFromStringUseCase
import com.gvelesiani.domain.useCases.passwords.AddNewPasswordUseCase
import com.gvelesiani.domain.useCases.passwords.GetPasswordsUseCase
import com.gvelesiani.helpers.helpers.encryptPassword.PasswordEncryptionHelper
import com.gvelesiani.helpers.helpers.resourceProvider.ResourceHelper
import com.gvelesiani.passworx.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BackupAndRestoreVM(
    private val getPasswordsFromStringUseCase: GetPasswordsFromStringUseCase,
    private val addNewPasswordUseCase: AddNewPasswordUseCase,
    private val getPasswordsUseCase: GetPasswordsUseCase,
    private val encryptionHelper: PasswordEncryptionHelper,
    private val resourceHelper: ResourceHelper
) : ViewModel() {
    private val _uiState = MutableStateFlow<BackupAndRestoreUiState>(BackupAndRestoreUiState.Empty)
    val uiState: StateFlow<BackupAndRestoreUiState> = _uiState

    init {
        getPasswords()
    }

    private fun getPasswords() {
        CoroutineScope(Dispatchers.IO).launch {
            getPasswordsUseCase(false).collect { passwords ->
                _uiState.value = BackupAndRestoreUiState.Success(passwords)
            }
        }
    }

    fun encryptPasswords(passwords: String): String {
        return encryptionHelper.encrypt(passwords)
    }

    fun insertPreviousPasswords(fileContent: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val passwords = getPasswordsFromStringUseCase(fileContent)
                for (password in passwords) {
                    addNewPasswordUseCase(password)
                }
                _uiState.value = BackupAndRestoreUiState.RestorePasswordsSuccess(
                    resourceHelper.getString(
                        R.string.restorePasswordsSuccess
                    ).format(passwords.size)
                )
            } catch (_: Exception) {
            }
        }
    }
}

sealed class BackupAndRestoreUiState {
    data class Success(val passwords: List<PasswordModel>) : BackupAndRestoreUiState()
    object Empty : BackupAndRestoreUiState()
    data class RestorePasswordsSuccess(val msg: String) : BackupAndRestoreUiState()
}