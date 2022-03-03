package com.gvelesiani.passworx.ui.passwordDetails

import androidx.lifecycle.MutableLiveData
import com.gvelesiani.passworx.base.BaseViewModel
import com.gvelesiani.passworx.helpers.encryptPassword.PasswordEncryptionHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PasswordDetailsViewModel(private val encryptionHelper: PasswordEncryptionHelper) :
    BaseViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    init {
        viewState.value = ViewState()
    }

    private fun currentViewState(): ViewState = viewState.value!!

    fun decryptPassword(password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = encryptionHelper.decrypt(password)
            viewState.postValue(currentViewState().copy(password = result))
        }
        return
    }

    data class ViewState(
        val password: String = ""
    )
}