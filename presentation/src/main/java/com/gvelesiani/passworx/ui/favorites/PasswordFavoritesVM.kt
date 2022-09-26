package com.gvelesiani.passworx.ui.favorites

import androidx.lifecycle.ViewModel
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.domain.useCases.encryption.DecryptPasswordUseCase
import com.gvelesiani.domain.useCases.passwords.GetFavoritePasswordsUseCase
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

class PasswordFavoritesVM(
    private val getFavoritePasswordsUseCase: GetFavoritePasswordsUseCase,
    private val updateItemTrashStateUseCase: UpdateItemTrashStateUseCase,
    private val updateFavoriteStateUseCase: UpdateFavoriteStateUseCase,
    private val decryptPasswordUseCase: DecryptPasswordUseCase,
    private val searchPasswordsUseCase: SearchPasswordsUseCase
) : ViewModel() {
    val decryptedPassword: MutableSharedFlow<String> = MutableSharedFlow()
    val passwords = MutableStateFlow<List<PasswordModel>>(listOf())
    private val _uiState = MutableStateFlow<FavoritesUIState>(FavoritesUIState.Empty)
    val uiState: StateFlow<FavoritesUIState> = _uiState

    init {
        getPasswords()
    }

    fun getPasswords() {
        _uiState.value = FavoritesUIState.Loading
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            getFavoritePasswordsUseCase.invoke(Unit).collect { response ->
                _uiState.value =
                    if (response.isEmpty())
                        FavoritesUIState.Empty
                    else
                        FavoritesUIState.Success(response)
            }
        }
    }

    fun searchPasswords(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            searchPasswordsUseCase(Pair(query, false)).collect { response ->
                _uiState.value = FavoritesUIState.Success(response)
            }
        }
    }

//    fun updateItemTrashState(isInTrash: Boolean, passwordId: Int) {
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                updateItemTrashStateUseCase.invoke(Pair(isInTrash, passwordId))
//                updateFavoriteStateUseCase.invoke(Pair(false, passwordId))
//                val result = getFavoritePasswordsUseCase(Unit)
//            } catch (e: Exception) {
//            }
//        }
//    }

    fun updateFavoriteState(isFavorite: Boolean = false, passwordId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            updateFavoriteStateUseCase(Pair(isFavorite, passwordId))
        }
    }

    fun decryptPassword(encryptedPassword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val password = decryptPasswordUseCase(encryptedPassword)
                decryptedPassword.tryEmit(password)
            } catch (e: Exception) {
            }
        }
    }
}

sealed class FavoritesUIState {
    data class Success(val passwords: List<PasswordModel>) : FavoritesUIState()
    object Empty : FavoritesUIState()
    object Loading : FavoritesUIState()
    data class Error(val errorMsg: String) : FavoritesUIState()
}