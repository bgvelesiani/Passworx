package com.gvelesiani.passworx.uiCompose.passwordDetails

import androidx.lifecycle.ViewModel
import com.gvelesiani.helpers.helpers.encryptPassword.PasswordEncryptionHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PasswordDetailsVM(private val encryptionHelper: PasswordEncryptionHelper) :
    ViewModel() {
    val decryptedPassword: MutableStateFlow<String> = MutableStateFlow("")

    fun decryptPassword(password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            decryptedPassword.value = encryptionHelper.decrypt(password)
        }
        return
    }
}