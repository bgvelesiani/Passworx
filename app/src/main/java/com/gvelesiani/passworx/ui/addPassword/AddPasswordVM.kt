package com.gvelesiani.passworx.ui.addPassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gvelesiani.domain.model.PasswordModel
import com.gvelesiani.domain.useCases.passwords.AddNewPasswordUseCase
import com.gvelesiani.helpers.helpers.encryptPassword.PasswordEncryptionHelper
import com.gvelesiani.helpers.helpers.resourceProvider.ResourceHelper
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.constants.MAX_TITLE_LENGTH
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddPasswordVM(
    private val addNewPasswordUseCase: AddNewPasswordUseCase,
    private val resourceHelper: ResourceHelper,
    private val encryptionHelper: PasswordEncryptionHelper
) : ViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    init {
        viewState.value = ViewState()
    }

    private fun currentViewState(): ViewState = viewState.value!!

    fun addNewPassword(password: PasswordModel) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                addNewPasswordUseCase(password)
            } catch (e: Exception) {
                viewState.postValue(
                    currentViewState().copy(
                        showAddNewPasswordError = resourceHelper.getString(
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

    data class ViewState(
        val isLoading: Boolean = false,
        val showTitleErrorMessage: String? = null,
        val showLabelErrorMessage: String? = null,
        val showAddNewPasswordError: String? = null,
        val addButtonEnabled: Boolean = false
    )
}