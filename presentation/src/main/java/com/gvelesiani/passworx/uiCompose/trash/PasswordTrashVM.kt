package com.gvelesiani.passworx.uiCompose.trash

import androidx.lifecycle.ViewModel
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.domain.useCases.passwords.DeletePasswordUseCase
import com.gvelesiani.domain.useCases.passwords.GetPasswordsUseCase
import com.gvelesiani.domain.useCases.passwords.SearchPasswordsUseCase
import com.gvelesiani.domain.useCases.passwords.UpdateItemTrashStateUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PasswordTrashVM(
    private val getPasswordsUseCase: GetPasswordsUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase,
    private val searchPasswordsUseCase: SearchPasswordsUseCase,
    private val updateItemTrashStateUseCase: UpdateItemTrashStateUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<TrashUIState>(TrashUIState.Empty)
    val uiState: StateFlow<TrashUIState> = _uiState


    fun getPasswords(isInTrash: Boolean = true) {
        _uiState.value = TrashUIState.Loading
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            getPasswordsUseCase(isInTrash).collect {
                _uiState.value = TrashUIState.Success(it)
            }
        }
    }

    fun searchPasswords(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            searchPasswordsUseCase(Pair(query, true)).collect {
                _uiState.value = TrashUIState.Success(it)
            }
        }
    }

    fun deletePassword(passwordId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            deletePasswordUseCase(passwordId)
        }
    }

    fun restorePassword(passwordId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            updateItemTrashStateUseCase(Pair(false, passwordId))
        }
    }
}

sealed class TrashUIState {
    data class Success(val passwords: List<PasswordModel>) : TrashUIState()
    object Empty : TrashUIState()
    object Loading : TrashUIState()
    data class Error(val errorMsg: String) : TrashUIState()
}