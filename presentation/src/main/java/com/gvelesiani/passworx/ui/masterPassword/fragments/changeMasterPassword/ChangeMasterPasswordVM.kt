package com.gvelesiani.passworx.ui.masterPassword.fragments.changeMasterPassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gvelesiani.domain.useCases.masterPassword.CreateOrChangeMasterPasswordUseCase
import com.gvelesiani.domain.useCases.masterPassword.GetMasterPasswordUseCase
import com.gvelesiani.helpers.helpers.hashPassword.PasswordHashHelper
import com.gvelesiani.helpers.helpers.validateMasterPassword.MasterPasswordValidatorHelper
import com.gvelesiani.helpers.helpers.validateMasterPassword.MasterPasswordValidatorHelperImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChangeMasterPasswordVM(
    private val createOrChangeMasterPassword: CreateOrChangeMasterPasswordUseCase,
    private val passwordHashHelper: PasswordHashHelper,
    private val getMasterPasswordUseCase: GetMasterPasswordUseCase,
    private val masterPasswordValidatorHelper: MasterPasswordValidatorHelper
) : ViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    init {
        viewState.value = ViewState()
        viewState.value =
            currentViewState().copy(validationErrors = masterPasswordValidatorHelper.getMasterPasswordErrors())
    }

    private fun currentViewState(): ViewState = viewState.value!!

    private fun changeMasterPassword(newMasterPassword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val masterPasswordModel = passwordHashHelper.hash(newMasterPassword)
                createOrChangeMasterPassword(masterPasswordModel)
                viewState.postValue(
                    currentViewState().copy(
                        validationErrors = null,
                        changeSuccess = true
                    )
                )
            } catch (ignored: Exception) {
                viewState.postValue(currentViewState().copy(showChangeMasterPasswordError = "Couldn't change master password, please try again"))
            }
        }
    }

    fun validate(
        masterPassword: String, newMasterPassword: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val masterPass = getMasterPasswordUseCase.invoke(Unit)
            if (!passwordHashHelper.verify(masterPassword, masterPass)) {
                viewState.postValue(currentViewState().copy(showPasswordVerifyError = "Please enter correct master password"))
            } else {
                viewState.postValue(currentViewState().copy(showPasswordVerifyError = null))
            }

            val isValid = passwordHashHelper.verify(
                masterPassword,
                masterPass
            ) && newMasterPassword.isNotEmpty()
            if (isValid) {
                changeMasterPassword(newMasterPassword = newMasterPassword)
            }
        }
    }

    fun validateNewPassword(newPassword: String) {
        viewState.value =
            currentViewState().copy(
                isValid = masterPasswordValidatorHelper.isValidPassword(
                    newPassword
                )
            )
    }

    data class ViewState(
        val showPasswordVerifyError: String? = null,
        val showChangeMasterPasswordError: String? = null,
        val validationErrors: MutableList<MasterPasswordValidatorHelperImpl.MasterPasswordError>? = null,
        val changeSuccess: Boolean = false,
        val isValid: Boolean = false
    )
}