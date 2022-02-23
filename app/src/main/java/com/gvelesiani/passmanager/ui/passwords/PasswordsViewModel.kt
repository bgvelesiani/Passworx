package com.gvelesiani.passmanager.ui.passwords

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gvelesiani.passmanager.base.BaseViewModel
import com.gvelesiani.passmanager.data.models.PasswordModel
import com.gvelesiani.passmanager.domain.useCases.GetPasswordsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PasswordsViewModel(private val getPasswordsUseCase: GetPasswordsUseCase) : BaseViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    init {
        viewState.value = ViewState()
    }

    private fun currentViewState(): ViewState = viewState.value!!

    fun getPasswords() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = getPasswordsUseCase.run(Unit)
                viewState.postValue(currentViewState().copy(passwords = result))
            } catch (e: Exception) {
                viewState.postValue(currentViewState().copy(showGetPasswordsError = "Couldn't get passwords"))
            }
        }
    }

    data class ViewState(
        val isLoading: Boolean = false,
        val showGetPasswordsError: String? = null,
        val passwords: List<PasswordModel> = listOf()
    )
}