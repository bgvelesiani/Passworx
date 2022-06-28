package com.gvelesiani.passworx.ui.passwords

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gvelesiani.passworx.data.models.PasswordModel
import com.gvelesiani.passworx.domain.useCases.GetPasswordsUseCase
import com.gvelesiani.passworx.domain.useCases.SearchPasswordsUseCase
import com.gvelesiani.passworx.domain.useCases.UpdateFavoriteStateUseCase
import com.gvelesiani.passworx.domain.useCases.UpdateItemTrashStateUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PasswordsVM(
    private val getPasswordsUseCase: GetPasswordsUseCase,
    private val updateFavoriteStateUseCase: UpdateFavoriteStateUseCase,
    private val updateItemTrashStateUseCase: UpdateItemTrashStateUseCase,
    private val searchPasswordsUseCase: SearchPasswordsUseCase
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
                val result = getPasswordsUseCase.invoke(params = false)
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

    fun updateFavoriteState(isFavorite: Boolean, passwordId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                updateFavoriteStateUseCase.invoke(Pair(!isFavorite, passwordId))
            } catch (e: Exception) {
                viewState.postValue(currentViewState().copy(showUpdatePasswordError = "Couldn't update password... please try again"))
            }
        }
    }

    fun searchPasswords(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val passwords = searchPasswordsUseCase.invoke(query)
                viewState.postValue(currentViewState().copy(passwords = passwords))
            } catch (e: Exception) {

            }
        }
    }

    fun updateItemTrashState(isInTrash: Boolean, passwordId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                updateItemTrashStateUseCase.invoke(Pair(isInTrash, passwordId))
            } catch (e: Exception) {
                viewState.postValue(currentViewState().copy(showTrashingItemError = "Couldn't move item to trash"))
            }
        }
    }

    data class ViewState(
        val isLoading: Boolean = false,
        val showGetPasswordsError: String? = null,
        val passwords: List<PasswordModel> = listOf(),
        val showUpdatePasswordError: String? = null,
        val showTrashingItemError: String? = null
    )
}