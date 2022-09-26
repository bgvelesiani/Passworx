package com.gvelesiani.passworx.ui.passwords

import androidx.lifecycle.ViewModel
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.domain.useCases.encryption.DecryptPasswordUseCase
import com.gvelesiani.domain.useCases.passwords.GetPasswordsUseCase
import com.gvelesiani.domain.useCases.passwords.SearchPasswordsUseCase
import com.gvelesiani.domain.useCases.passwords.UpdateFavoriteStateUseCase
import com.gvelesiani.domain.useCases.passwords.UpdateItemTrashStateUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PasswordsVM(
    private val getPasswordsUseCase: GetPasswordsUseCase,
    private val updateFavoriteStateUseCase: UpdateFavoriteStateUseCase,
    private val updateItemTrashStateUseCase: UpdateItemTrashStateUseCase,
    private val searchPasswordsUseCase: SearchPasswordsUseCase,
    private val decryptPasswordUseCase: DecryptPasswordUseCase,
) : ViewModel() {
    val decryptedPassword: MutableSharedFlow<String> = MutableSharedFlow()

    private val _uiState = MutableStateFlow<PasswordsUIState>(PasswordsUIState.Empty)
    val uiState: StateFlow<PasswordsUIState> = _uiState

    fun getPasswords() {
        _uiState.value = PasswordsUIState.Loading
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            getPasswordsUseCase(false).collect { response ->
                _uiState.value =
                    if (response.isEmpty()) PasswordsUIState.Empty else PasswordsUIState.Success(
                        response
                    )
            }
        }
    }

    fun searchPasswords(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            searchPasswordsUseCase(Pair(query, false)).collect { response ->
                _uiState.value = PasswordsUIState.Success(response)
            }
        }
    }

    fun updateFavoriteState(isFavorite: Boolean, passwordId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            updateFavoriteStateUseCase(Pair(isFavorite, passwordId))
        }
    }

    fun updateItemTrashState(isInTrash: Boolean, isFavorite: Boolean, passwordId: Int) {
        _uiState.value = PasswordsUIState.Loading
        CoroutineScope(Dispatchers.IO).launch {
            updateItemTrashStateUseCase(Pair(isInTrash, passwordId))
            if (isFavorite) {
                updateFavoriteStateUseCase(Pair(false, passwordId))
            }
        }
    }

    fun decryptPassword(encryptedPassword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val password = decryptPasswordUseCase(encryptedPassword)
                decryptedPassword.emit(password)
            } catch (e: Exception) {
            }
        }
    }
}

sealed class PasswordsUIState {
    data class Success(val passwords: List<PasswordModel>) : PasswordsUIState()
    object Empty : PasswordsUIState()
    object Loading : PasswordsUIState()
    data class Error(val errorMsg: String) : PasswordsUIState()
}