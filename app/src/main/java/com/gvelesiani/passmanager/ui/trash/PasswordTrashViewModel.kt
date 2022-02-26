package com.gvelesiani.passmanager.ui.trash

import androidx.lifecycle.MutableLiveData
import com.gvelesiani.passmanager.base.BaseViewModel
import com.gvelesiani.passmanager.data.models.PasswordModel
import com.gvelesiani.passmanager.domain.useCases.DeletePasswordUseCase
import com.gvelesiani.passmanager.domain.useCases.GetPasswordsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PasswordTrashViewModel(
    private val getPasswordsUseCase: GetPasswordsUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase
) :
    BaseViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    init {
        viewState.value = ViewState()
    }

    private fun currentViewState(): ViewState = viewState.value!!
    fun getPasswords(isInTrash: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            viewState.postValue(currentViewState().copy(isLoading = true))
            try {
                val result = getPasswordsUseCase.run(isInTrash)
                viewState.postValue(currentViewState().copy(passwords = result, isLoading = false))
            } catch (e: Exception) {
                viewState.postValue(currentViewState().copy(showGetPasswordsError = "Couldn't get passwords"))
            }
        }
    }

    fun deletePassword(passwordId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            viewState.postValue(currentViewState().copy(isLoading = true))
            try {
                deletePasswordUseCase.run(passwordId)
                getPasswords(isInTrash = true)
            } catch (e: Exception) {
                viewState.postValue(currentViewState().copy(showDeletePasswordsError = "Couldn't delete passwords"))
            }
        }
    }

    data class ViewState(
        val isLoading: Boolean = false,
        val showGetPasswordsError: String? = null,
        val showDeletePasswordsError: String? = null,
        val passwords: List<PasswordModel> = listOf(),
    )
}