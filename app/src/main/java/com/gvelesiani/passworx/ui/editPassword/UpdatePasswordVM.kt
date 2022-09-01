package com.gvelesiani.passworx.ui.editPassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.constants.MAX_TITLE_LENGTH
import com.gvelesiani.passworx.domain.model.PasswordModel
import com.gvelesiani.passworx.domain.useCases.passwords.UpdatePasswordUseCase
import com.gvelesiani.passworx.helpers.encryptPassword.PasswordEncryptionHelper
import com.gvelesiani.passworx.helpers.resourceProvider.ResourceHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdatePasswordVM(
    private val encryptionHelper: PasswordEncryptionHelper,
    private val resourceHelper: ResourceHelper,
    private val editPasswordUseCase: UpdatePasswordUseCase,
) : ViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    init {
        viewState.value = ViewState()
    }

    private fun currentViewState(): ViewState = viewState.value!!

    fun editPassword(password: PasswordModel) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                editPasswordUseCase(password)
            } catch (e: Exception) {
                viewState.postValue(
                    currentViewState().copy(
                        showEditPasswordError = resourceHelper.getString(
                            R.string.add_password_error_Text
                        )
                    )
                )
            }
        }
    }

    fun onTitleChanged(title: String) {
        if (title.length > MAX_TITLE_LENGTH) {
            viewState.value = currentViewState().copy(
                showTitleErrorMessage = "Max character limit reached",
                addButtonEnabled = false
            )
        } else {
            viewState.value =
                currentViewState().copy(showTitleErrorMessage = null, addButtonEnabled = true)
        }
    }

    fun onLabelChanged(label: String) {
        if (label.length > MAX_TITLE_LENGTH) {
            viewState.value = currentViewState().copy(
                showLabelErrorMessage = "Max character limit reached",
                addButtonEnabled = false
            )
        } else {
            viewState.value =
                currentViewState().copy(showLabelErrorMessage = null, addButtonEnabled = true)
        }
    }

    fun encryptPassword(password: String): String {
        return encryptionHelper.encrypt(password)
    }

    fun decryptPassword(password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = encryptionHelper.decrypt(password)
            viewState.postValue(currentViewState().copy(password = result))
        }
        return
    }

    data class ViewState(
        val password: String = "",
        val isLoading: Boolean = false,
        val showTitleErrorMessage: String? = null,
        val showLabelErrorMessage: String? = null,
        val showEditPasswordError: String? = null,
        val addButtonEnabled: Boolean = true
    )

}