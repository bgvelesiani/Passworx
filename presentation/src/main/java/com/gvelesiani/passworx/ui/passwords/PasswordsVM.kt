package com.gvelesiani.passworx.ui.passwords

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gvelesiani.domain.useCases.encryption.DecryptPasswordUseCase
import com.gvelesiani.domain.useCases.passwords.GetPasswordsUseCase
import com.gvelesiani.domain.useCases.passwords.SearchPasswordsUseCase
import com.gvelesiani.domain.useCases.passwords.UpdateFavoriteStateUseCase
import com.gvelesiani.domain.useCases.passwords.UpdateItemTrashStateUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PasswordsVM(
    private val getPasswordsUseCase: GetPasswordsUseCase,
    private val updateFavoriteStateUseCase: UpdateFavoriteStateUseCase,
    private val updateItemTrashStateUseCase: UpdateItemTrashStateUseCase,
    private val searchPasswordsUseCase: SearchPasswordsUseCase,
    private val decryptPasswordUseCase: DecryptPasswordUseCase,
) : ViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    init {
        viewState.value = ViewState()
    }

    private fun currentViewState(): ViewState = viewState.value!!

    fun getPasswords() {
        viewState.value = currentViewState().copy(isLoading = true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                delay(100)
                val result = getPasswordsUseCase(params = false)
                viewState.postValue(
                    currentViewState().copy(
                        passwords = result,
                        isLoading = false
                    )
                )
            } catch (e: Exception) {
                viewState.postValue(
                    currentViewState().copy(
                        isLoading = false,
                        showGetPasswordsError = "Couldn't get passwords"
                    )
                )
            }
        }
    }

    fun searchPasswords(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val passwords = searchPasswordsUseCase(Pair(query, false))
                viewState.postValue(currentViewState().copy(passwords = passwords))
            } catch (e: Exception) {
            }
        }
    }

    fun updateFavoriteState(isFavorite: Boolean, passwordId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                delay(100)
                updateFavoriteStateUseCase(Pair(isFavorite, passwordId))
                val passwords = getPasswordsUseCase(false)
                viewState.postValue(
                    currentViewState().copy(
                        passwords = passwords
                    )
                )
            } catch (e: Exception) {
                viewState.postValue(currentViewState().copy(showUpdatePasswordError = "Couldn't update password... please try again"))
            }
        }
    }

    fun updateItemTrashState(isInTrash: Boolean, isFavorite: Boolean, passwordId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                delay(100)
                updateItemTrashStateUseCase(Pair(isInTrash, passwordId))
                if (isFavorite) {
                    updateFavoriteStateUseCase(Pair(false, passwordId))
                }
                val passwords = getPasswordsUseCase(false)
                viewState.postValue(
                    currentViewState().copy(
                        passwords = passwords
                    )
                )
            } catch (e: Exception) {
                viewState.postValue(currentViewState().copy(showTrashingItemError = "Couldn't move item to trash"))
            }
        }
    }

    fun decryptPassword(encryptedPassword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val decryptedPassword = decryptPasswordUseCase(encryptedPassword)
                viewState.postValue(currentViewState().copy(decryptedPassword = decryptedPassword))
            } catch (e: Exception) {
                viewState.postValue(currentViewState().copy(showDecryptionError = "Couldn't decrypt password"))
            }
        }
    }

    data class ViewState(
        val showDecryptionError: String? = null,
        val decryptedPassword: String? = null,
        val isLoading: Boolean = false,
        val showGetPasswordsError: String? = null,
        val passwords: List<com.gvelesiani.common.models.domain.PasswordModel> = listOf(),
        val showUpdatePasswordError: String? = null,
        val showTrashingItemError: String? = null
    )
}