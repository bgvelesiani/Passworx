package com.gvelesiani.passworx.ui.favourites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gvelesiani.passworx.domain.model.PasswordModel
import com.gvelesiani.passworx.domain.useCases.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PasswordFavouritesVM(
    private val getPasswordsUseCase: GetPasswordsUseCase,
    private val updateItemTrashStateUseCase: UpdateItemTrashStateUseCase,
    private val updateFavoriteStateUseCase: UpdateFavoriteStateUseCase,
    private val decryptPasswordUseCase: DecryptPasswordUseCase,
    private val searchPasswordsUseCase: SearchPasswordsUseCase
) : ViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    init {
        viewState.value = ViewState()
        getPasswords()
    }

    private fun currentViewState(): ViewState = viewState.value!!
    fun getPasswords(isInTrash: Boolean = false,isInFavourites: Boolean = true) {
        viewState.value = currentViewState().copy(isLoading = true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                delay(100)
                val result = getPasswordsUseCase(Pair(isInTrash, isInFavourites))
                viewState.postValue(currentViewState().copy(passwords = result, isLoading = false))
            } catch (e: Exception) {
                viewState.postValue(
                    currentViewState().copy(
                        showGetPasswordsError = "Couldn't get passwords",
                        isLoading = false
                    )
                )
            }
        }
    }

    fun searchPasswords(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val passwords = searchPasswordsUseCase.invoke(Pair(query, true))
                viewState.postValue(currentViewState().copy(passwords = passwords))
            } catch (e: Exception) {
            }
        }
    }

    fun updateItemTrashState(isInTrash: Boolean, isInFavourites: Boolean, passwordId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                updateItemTrashStateUseCase.invoke(Pair(isInTrash, passwordId))
                updateFavoriteStateUseCase.invoke(Pair(isInFavourites, passwordId))
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
        val showTrashingItemError: String? = null,
        val isLoading: Boolean = false,
        val showGetPasswordsError: String? = null,
        val showDeletePasswordsError: String? = null,
        val passwords: List<PasswordModel> = listOf()
    )
}