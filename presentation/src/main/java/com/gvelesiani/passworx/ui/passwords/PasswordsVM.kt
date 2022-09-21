package com.gvelesiani.passworx.ui.passwords

import androidx.lifecycle.ViewModel
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.domain.useCases.encryption.DecryptPasswordUseCase
import com.gvelesiani.domain.useCases.passwords.GetPasswordsUseCase
import com.gvelesiani.domain.useCases.passwords.SearchPasswordsUseCase
import com.gvelesiani.domain.useCases.passwords.UpdateFavoriteStateUseCase
import com.gvelesiani.domain.useCases.passwords.UpdateItemTrashStateUseCase
import com.gvelesiani.passworx.common.ActionLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PasswordsVM(
    private val getPasswordsUseCase: GetPasswordsUseCase,
    private val updateFavoriteStateUseCase: UpdateFavoriteStateUseCase,
    private val updateItemTrashStateUseCase: UpdateItemTrashStateUseCase,
    private val searchPasswordsUseCase: SearchPasswordsUseCase,
    private val decryptPasswordUseCase: DecryptPasswordUseCase,
) : ViewModel() {
    val decryptedPassword: ActionLiveData<String> = ActionLiveData()
    val passwords = MutableStateFlow<List<PasswordModel>>(listOf())
    val isLoading = MutableStateFlow(false)

    fun getPasswords() {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                delay(1000)
                isLoading.value = false
                val result = getPasswordsUseCase(params = false)
                passwords.value = result
            } catch (e: Exception) {
            }
        }
    }

    fun searchPasswords(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = searchPasswordsUseCase(Pair(query, false))
                passwords.value = result
            } catch (e: Exception) {
            }
        }
    }

    fun updateFavoriteState(isFavorite: Boolean, passwordId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                delay(100)
                updateFavoriteStateUseCase(Pair(isFavorite, passwordId))
                val result = getPasswordsUseCase(false)
                passwords.value = result
            } catch (e: Exception) {
            }
        }
    }

    fun updateItemTrashState(isInTrash: Boolean, isFavorite: Boolean, passwordId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                delay(100)
                updateItemTrashStateUseCase(Pair(isInTrash, passwordId))
                if (isFavorite) {
                    updateFavoriteStateUseCase(Pair(false, passwordId))
                }
                passwords.value = getPasswordsUseCase(false)
            } catch (e: Exception) {
            }
        }
    }

    fun decryptPassword(encryptedPassword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val password = decryptPasswordUseCase(encryptedPassword)
                decryptedPassword.postValue(password)
            } catch (e: Exception) {
            }
        }
    }
}