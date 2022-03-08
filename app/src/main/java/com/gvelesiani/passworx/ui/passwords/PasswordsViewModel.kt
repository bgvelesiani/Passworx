package com.gvelesiani.passworx.ui.passwords

import androidx.lifecycle.MutableLiveData
import com.gvelesiani.passworx.base.BaseViewModel
import com.gvelesiani.passworx.data.models.PasswordModel
import com.gvelesiani.passworx.domain.useCases.GetPasswordsUseCase
import com.gvelesiani.passworx.domain.useCases.UpdateFavoriteStateUseCase
import com.gvelesiani.passworx.domain.useCases.UpdateItemTrashStateUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PasswordsViewModel(
    private val getPasswordsUseCase: GetPasswordsUseCase,
    private val updateFavoriteStateUseCase: UpdateFavoriteStateUseCase,
    private val updateItemTrashStateUseCase: UpdateItemTrashStateUseCase
) : BaseViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    init {
        viewState.value = ViewState()
    }

    private fun currentViewState(): ViewState = viewState.value!!

    private fun makeNoDataInfoVisible(visible: Boolean) {
        viewState.value = currentViewState().copy(isNoDataInfoVisible = visible)
    }

    fun getPasswords(isInTrash: Boolean = false) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = getPasswordsUseCase.run(params = isInTrash)
                viewState.postValue(currentViewState().copy(passwords = result))
            } catch (e: Exception) {
                viewState.postValue(currentViewState().copy(showGetPasswordsError = "Couldn't get passwords"))
            }
        }
    }

    fun updateFavoriteState(isFavorite: Boolean, passwordId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                delay(1000)
                updateFavoriteStateUseCase.run(Pair(!isFavorite, passwordId))
            } catch (e: Exception) {
                viewState.postValue(currentViewState().copy(showUpdatePasswordError = "Couldn't update password... please try again"))
            }
        }
    }

    fun updateItemTrashState(isInTrash: Boolean, passwordId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                updateItemTrashStateUseCase.run(Pair(isInTrash, passwordId))
                getPasswords(isInTrash = false)
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
        val showTrashingItemError: String? = null,
        val isNoDataInfoVisible: Boolean = false
    )
}