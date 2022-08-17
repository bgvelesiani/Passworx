package com.gvelesiani.passworx.ui.backupAndRestore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gvelesiani.passworx.domain.model.PasswordModel
import com.gvelesiani.passworx.domain.useCases.AddNewPasswordUseCase
import com.gvelesiani.passworx.domain.useCases.GetPasswordsFromStringUseCase
import com.gvelesiani.passworx.domain.useCases.GetPasswordsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BackupAndRestoreVM(
    private val getPasswordsFromStringUseCase: GetPasswordsFromStringUseCase,
    private val addNewPasswordUseCase: AddNewPasswordUseCase,
    private val getPasswordsUseCase: GetPasswordsUseCase
) : ViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    init {
        viewState.value = ViewState()
        getPasswords()
    }

    private fun currentViewState(): ViewState = viewState.value!!

    private fun getPasswords() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = getPasswordsUseCase(false)
                viewState.postValue(currentViewState().copy(passwords = result))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun insertPreviousPasswords(fileContent: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val passwords = getPasswordsFromStringUseCase(fileContent)
                for (password in passwords) {
                    addNewPasswordUseCase(password)
                }
            } catch (e: Exception) {
            }
        }
    }

    data class ViewState(
        val passwords: List<PasswordModel> = listOf()
    )
}