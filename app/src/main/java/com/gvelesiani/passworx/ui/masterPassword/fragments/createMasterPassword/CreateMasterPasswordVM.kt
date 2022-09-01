package com.gvelesiani.passworx.ui.masterPassword.fragments.createMasterPassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gvelesiani.passworx.domain.useCases.masterPassword.CreateOrChangeMasterPasswordUseCase
import com.gvelesiani.passworx.helpers.hashPassword.PasswordHashHelper
import com.gvelesiani.passworx.helpers.validateMasterPassword.MasterPasswordValidatorHelper
import com.gvelesiani.passworx.helpers.validateMasterPassword.MasterPasswordValidatorHelperImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CreateMasterPasswordVM(
    private val createOrChangeMasterPassword: CreateOrChangeMasterPasswordUseCase,
    private val passwordHashHelper: PasswordHashHelper,
    private val masterPasswordValidatorHelper: MasterPasswordValidatorHelper
) : ViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    init {
        viewState.value = ViewState()
        viewState.value =
            currentViewState().copy(validationErrors = masterPasswordValidatorHelper.getMasterPasswordErrors())
    }

    private fun currentViewState(): ViewState = viewState.value!!

    fun createMasterPassword(masterPassword: String) {
        viewState.value = currentViewState().copy(isLoading = true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                delay(500L)
                val masterPasswordModel = passwordHashHelper.hash(masterPassword)
                createOrChangeMasterPassword.invoke(masterPasswordModel)
                viewState.postValue(
                    currentViewState().copy(
                        validationErrors = null,
                        validationSuccess = true,
                        isLoading = false
                    )
                )
            } catch (ignored: Exception) {
                viewState.postValue(currentViewState().copy(showCreateMasterPasswordError = "Couldn't create master password, please try again"))
            }
        }
    }

    fun validate(masterPassword: String) {
        viewState.postValue(
            currentViewState().copy(
                isValid = masterPasswordValidatorHelper.isValidPassword(
                    masterPassword
                )
            )
        )
    }

    data class ViewState(
        val showCreateMasterPasswordError: String? = null,
        val validationErrors: MutableList<MasterPasswordValidatorHelperImpl.MasterPasswordError>? = null,
        val validationSuccess: Boolean = false,
        val isValid: Boolean = false,
        val isLoading: Boolean = false
    )
}