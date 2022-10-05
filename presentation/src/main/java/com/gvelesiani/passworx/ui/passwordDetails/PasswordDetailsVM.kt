package com.gvelesiani.passworx.ui.passwordDetails

import androidx.lifecycle.ViewModel
import com.gvelesiani.domain.useCases.passwords.UpdateFavoriteStateUseCase
import com.gvelesiani.domain.useCases.passwords.UpdateItemTrashStateUseCase
import com.gvelesiani.helpers.helpers.encryptPassword.PasswordEncryptionHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PasswordDetailsVM(
    private val encryptionHelper: PasswordEncryptionHelper,
    private val updateItemTrashStateUseCase: UpdateItemTrashStateUseCase,
    private val updateFavoriteStateUseCase: UpdateFavoriteStateUseCase
) :
    ViewModel() {
    val decryptedPassword: MutableStateFlow<String> = MutableStateFlow("")

    fun decryptPassword(password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            decryptedPassword.value = encryptionHelper.decrypt(password)
        }
        return
    }

    fun deletePassword(isInTrash: Boolean = true, isFavorite: Boolean, passwordId: Int) {
//        _uiState.value = PasswordsUIState.Loading
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            updateItemTrashStateUseCase(Pair(isInTrash, passwordId))
            if (isFavorite) {
                updateFavoriteStateUseCase(Pair(false, passwordId))
            }
        }
    }

//    fun decryptPassword(encryptedPassword: String) {
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val password = decryptPasswordUseCase(encryptedPassword)
//                decryptedPassword.emit(password)
//            } catch (e: Exception) {
//            }
//        }
//    }
}