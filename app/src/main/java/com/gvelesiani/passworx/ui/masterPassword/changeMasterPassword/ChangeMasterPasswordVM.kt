package com.gvelesiani.passworx.ui.masterPassword.changeMasterPassword

import androidx.lifecycle.MutableLiveData
import com.gvelesiani.passworx.base.BaseViewModel
import com.gvelesiani.passworx.domain.useCases.CreateOrChangeMasterPasswordUseCase
import com.gvelesiani.passworx.domain.useCases.GetMasterPasswordUseCase
import com.gvelesiani.passworx.helpers.hashPassword.PasswordHashHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChangeMasterPasswordVM(
    private val createOrChangeMasterPassword: CreateOrChangeMasterPasswordUseCase,
    private val passwordHashHelper: PasswordHashHelper,
    private val getMasterPasswordUseCase: GetMasterPasswordUseCase
) : BaseViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    init {
        viewState.value = ViewState()
    }

    private fun currentViewState(): ViewState = viewState.value!!

    fun changeMasterPassword(masterPassword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val masterPasswordModel = passwordHashHelper.hash(masterPassword)
                createOrChangeMasterPassword.run(masterPasswordModel)
            } catch (ignored: Exception) {
                viewState.postValue(currentViewState().copy(showCreateMasterPasswordError = "Couldn't create master password, please try again"))
            }
        }
    }

    data class ViewState(
        val showCreateMasterPasswordError: String? = null,
    )
}