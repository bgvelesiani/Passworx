package com.gvelesiani.passworx.uiCompose.updatePassword

import androidx.lifecycle.ViewModel
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.domain.useCases.passwords.UpdatePasswordUseCase
import com.gvelesiani.helpers.helpers.encryptPassword.PasswordEncryptionHelper
import com.gvelesiani.passworx.constants.MAX_TITLE_LENGTH
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UpdatePasswordVM(
    private val encryptionHelper: PasswordEncryptionHelper,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UpdatePasswordUiState>(UpdatePasswordUiState.Empty)
    val uiState: StateFlow<UpdatePasswordUiState> = _uiState

    fun updatePassword(password: PasswordModel) {
        CoroutineScope(Dispatchers.IO).launch {
            updatePasswordUseCase(password)
            _uiState.value = UpdatePasswordUiState.Success
        }
    }

    fun onTitleChanged(title: String) {
        if (title.length > MAX_TITLE_LENGTH) {

        }
    }

    fun onLabelChanged(label: String) {
        if (label.length > MAX_TITLE_LENGTH) {

        }
    }

    fun encryptPassword(password: String): String {
        return encryptionHelper.encrypt(password)
    }

    fun decryptPassword(password: String): String {
        return encryptionHelper.decrypt(password)
    }
}

sealed class UpdatePasswordUiState {
    object Success: UpdatePasswordUiState()
    object Empty: UpdatePasswordUiState()
}