package com.gvelesiani.passworx.ui.masterPassword.createMasterPassword

import androidx.lifecycle.MutableLiveData
import com.gvelesiani.passworx.base.BaseViewModel
import com.gvelesiani.passworx.domain.useCases.CreateMasterPasswordUseCase
import com.gvelesiani.passworx.helpers.hashPassword.PasswordHashHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateMasterPasswordVM(
    private val createMasterPassword: CreateMasterPasswordUseCase,
    private val passwordHashHelper: PasswordHashHelper
) : BaseViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    init {
        viewState.value = ViewState()
    }

    private fun currentViewState(): ViewState = viewState.value!!

    fun createMasterPassword(masterPassword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val masterPasswordModel = passwordHashHelper.hash(masterPassword)
                createMasterPassword.run(masterPasswordModel)
            } catch (ignored: Exception) {
                viewState.postValue(currentViewState().copy(showCreateMasterPasswordError = "Couldn't create master password, please try again"))
            }
        }
    }

    data class ViewState(
        val showCreateMasterPasswordError: String? = null,
    )
}