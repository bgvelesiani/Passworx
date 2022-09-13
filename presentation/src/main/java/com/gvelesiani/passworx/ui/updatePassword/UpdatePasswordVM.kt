package com.gvelesiani.passworx.ui.updatePassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gvelesiani.domain.useCases.passwords.UpdatePasswordUseCase
import com.gvelesiani.helpers.helpers.encryptPassword.PasswordEncryptionHelper
import com.gvelesiani.helpers.helpers.resourceProvider.ResourceHelper
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.constants.MAX_TITLE_LENGTH
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdatePasswordVM(
    private val encryptionHelper: PasswordEncryptionHelper,
    private val resourceHelper: ResourceHelper,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
) : ViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    init {
        viewState.value = ViewState()
    }

    private fun currentViewState(): ViewState = viewState.value!!

    fun updatePassword(password: com.gvelesiani.common.models.domain.PasswordModel) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                updatePasswordUseCase(password)
            } catch (e: Exception) {
                viewState.postValue(
                    currentViewState().copy(
                        showUpdatePasswordError = resourceHelper.getString(
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
        val showUpdatePasswordError: String? = null,
        val addButtonEnabled: Boolean = true
    )

}