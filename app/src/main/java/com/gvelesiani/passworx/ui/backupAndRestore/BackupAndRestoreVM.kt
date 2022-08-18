package com.gvelesiani.passworx.ui.backupAndRestore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.domain.model.PasswordModel
import com.gvelesiani.passworx.domain.useCases.AddNewPasswordUseCase
import com.gvelesiani.passworx.domain.useCases.GetPasswordsFromStringUseCase
import com.gvelesiani.passworx.domain.useCases.GetPasswordsUseCase
import com.gvelesiani.passworx.helpers.encryptPassword.PasswordEncryptionHelper
import com.gvelesiani.passworx.helpers.resourceProvider.ResourceHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BackupAndRestoreVM(
    private val getPasswordsFromStringUseCase: GetPasswordsFromStringUseCase,
    private val addNewPasswordUseCase: AddNewPasswordUseCase,
    private val getPasswordsUseCase: GetPasswordsUseCase,
    private val encryptionHelper: PasswordEncryptionHelper,
    private val resourceHelper: ResourceHelper
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

    fun encryptPasswords(passwords: String): String {
        return encryptionHelper.encrypt(passwords)
    }

    fun insertPreviousPasswords(fileContent: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val passwords = getPasswordsFromStringUseCase(fileContent)
                for (password in passwords) {
                    addNewPasswordUseCase(password)
                }
                viewState.postValue(
                    currentViewState().copy(
                        restorePasswordsSuccess = resourceHelper.getString(
                            R.string.restorePasswordsSuccess
                        ).format(passwords.size)
                    )
                )
            } catch (e: Exception) {
            }
        }
    }

    data class ViewState(
        val passwords: List<PasswordModel> = listOf(),
        val restorePasswordsSuccess: String? = null
    )
}