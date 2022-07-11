package com.gvelesiani.passworx.ui.trash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gvelesiani.passworx.domain.model.PasswordModel
import com.gvelesiani.passworx.domain.useCases.DeletePasswordUseCase
import com.gvelesiani.passworx.domain.useCases.GetPasswordsUseCase
import com.gvelesiani.passworx.domain.useCases.SearchPasswordsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PasswordTrashVM(
    private val getPasswordsUseCase: GetPasswordsUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase,
    private val searchPasswordsUseCase: SearchPasswordsUseCase
) : ViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    init {
        viewState.value = ViewState()
        getPasswords()
    }

    private fun currentViewState(): ViewState = viewState.value!!
    fun getPasswords(isInTrash: Boolean = true) {
        viewState.value = currentViewState().copy(isLoading = true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                delay(100)
                val result = getPasswordsUseCase(isInTrash)
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

    fun deletePassword(passwordId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                deletePasswordUseCase(passwordId)
                val result = getPasswordsUseCase(params = true)
                viewState.postValue(currentViewState().copy(passwords = result))
            } catch (e: Exception) {
                viewState.postValue(
                    currentViewState().copy(
                        showDeletePasswordsError = "Couldn't delete passwords",
                    )
                )
            }
        }
    }

    data class ViewState(
        val isLoading: Boolean = false,
        val showGetPasswordsError: String? = null,
        val showDeletePasswordsError: String? = null,
        val passwords: List<PasswordModel> = listOf()
    )
}