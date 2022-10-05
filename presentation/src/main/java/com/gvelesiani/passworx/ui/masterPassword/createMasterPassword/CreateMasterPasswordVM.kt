package com.gvelesiani.passworx.ui.masterPassword.createMasterPassword

import androidx.lifecycle.ViewModel
import com.gvelesiani.domain.useCases.masterPassword.CreateOrChangeMasterPasswordUseCase
import com.gvelesiani.helpers.helpers.hashPassword.PasswordHashHelper
import com.gvelesiani.helpers.helpers.validateMasterPassword.MasterPasswordValidatorHelper
import com.gvelesiani.helpers.helpers.validateMasterPassword.MasterPasswordValidatorHelperImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CreateMasterPasswordVM(
    private val createOrChangeMasterPassword: CreateOrChangeMasterPasswordUseCase,
    private val passwordHashHelper: PasswordHashHelper,
    private val masterPasswordValidatorHelper: MasterPasswordValidatorHelper
) : ViewModel() {

    private val _validationErrors: MutableStateFlow<MutableList<MasterPasswordValidatorHelperImpl.MasterPasswordError>> =
        MutableStateFlow(mutableListOf())
    val validationErrors: MutableStateFlow<MutableList<MasterPasswordValidatorHelperImpl.MasterPasswordError>> =
        _validationErrors

    private val _passwordCreated: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val passwordCreated: MutableStateFlow<Boolean> = _passwordCreated

    private val _isValid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isValid: MutableStateFlow<Boolean> = _isValid

    init {
        _validationErrors.value = masterPasswordValidatorHelper.getMasterPasswordErrors()
    }

    fun createMasterPassword(masterPassword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(500L)
            if(isValid.value){
                val masterPasswordModel = passwordHashHelper.hash(masterPassword)
                createOrChangeMasterPassword.invoke(masterPasswordModel)
                _passwordCreated.value = true
            }
        }
    }

    fun validate(masterPassword: String) {
        _isValid.value = masterPasswordValidatorHelper.isValidPassword(
            masterPassword
        )
    }
}